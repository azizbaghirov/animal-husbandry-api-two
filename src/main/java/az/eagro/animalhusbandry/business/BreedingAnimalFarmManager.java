package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.api.service.model.FindBreedingAnimalFarmDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import az.eagro.animalhusbandry.repository.BreedingAnimalFarmRepository;
import az.eagro.animalhusbandry.repository.BreedingAnimalFarmSpecializationRepository;
import az.eagro.animalhusbandry.shared.ektis.EktisClient;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BreedingAnimalFarmManager {

    private final BreedingAnimalFarmValidation farmValidation;
    private final BreedingAnimalFarmRepository farmRepository;
    private final AdministrativeAreaManager administrativeAreaManager;
    private final RegionManager regionManager;
    private final EktisClient ektisClient;
    private final EntityManagerFactory managerFactory;
    private final FarmerAccountManager farmerAccountManager;
    private final BreedingAnimalFarmSpecializationRepository farmSpecializationRepository;
    private final AnimalValidation animalValidation;
    private final FarmTypeValidation farmTypeValidation;

    @Transactional
    public void createFarm(BreedingAnimalFarmEntity newFarm) {

        farmTypeValidation.ensureHasFarmType(newFarm.getFarmType().getId());
        animalValidation.ensureExistsAnimal(newFarm.getAnimalSort().getId(), newFarm.getAnimalType().getId(), newFarm.getFarmType().getId());
        farmValidation.ensureNewFarmIsValid(newFarm);

        var farmer = farmerAccountManager.findFarmerAccount();
        newFarm.setFarmerAccount(farmer);
        newFarm.setDeletable(true);

        var reg = regionManager.findById(newFarm.getAdministrativeArea().getRegion().getId()).orElse(null);
        if (ObjectUtils.isEmpty(reg)) {
            var optionalRegion = ektisClient.getRegions().stream()
                    .filter(r -> Objects.equals(r.getId(), newFarm.getAdministrativeArea().getRegion().getId()))
                    .findFirst();
            if (optionalRegion.isPresent()) {
                var regionDto = optionalRegion.get();
                var region = newFarm.getAdministrativeArea().getRegion();
                region.setCode(regionDto.getCode());
                region.setName(regionDto.getName());
                region.setId(regionDto.getId());
                reg = regionManager.saveRegion(region);
            } else {
                throw new BusinessException("(EKTİS) Region mövcud deyil.");
            }
        }
        newFarm.setRegion(reg);


        if (!administrativeAreaManager.findById(newFarm.getAdministrativeArea().getId()).isPresent()) {
            var administrativeAreas = ektisClient.getAdministrativeAreas(newFarm.getAdministrativeArea().getRegion().getId());
            var optionalAdministrativeArea = administrativeAreas.stream()
                    .filter(a -> Objects.equals(a.getId(), newFarm.getAdministrativeArea().getId()))
                    .findFirst();
            if (optionalAdministrativeArea.isPresent()) {
                var administrativeAreaDto = optionalAdministrativeArea.get();
                var administrativeArea = newFarm.getAdministrativeArea();
                administrativeArea.setName(administrativeAreaDto.getName());
                administrativeArea.setCode(administrativeAreaDto.getCode());
                administrativeAreaManager.saveAdministrativeArea(administrativeArea);
            } else {
                throw new BusinessException("İnzibati ərazi mövcud deyil.");
            }
        }
        farmRepository.save(newFarm);
    }

    public Page<BreedingAnimalFarmEntity> getFarmListByRegionIdAndFarmTypeId(FindBreedingAnimalFarmDTO dto, Pageable pageRequest) {
        var farmer = farmerAccountManager.findFarmerAccount();

        return farmRepository.findAllByRegionIdAndFarmTypeIdAndFarmerAccountIdAndDeletedAtIsNullOrderByCreatedAtDesc(
                dto.getRegionId(), dto.getFarmTypeId(), farmer.getId(), pageRequest);
    }

    public BreedingAnimalFarmEntity getByIdAndFarmer(Integer id) {
        return farmRepository.findByIdAndFarmerAccountId(id, farmerAccountManager.findFarmerAccount().getId())
                .orElseThrow(() -> new BusinessException("İstifadəçiyə uyğun təsərrüfat tapılmadı"));
    }

    public BreedingAnimalFarmEntity getById(Integer id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Təsərrüfat tapılmadı"));
    }

    @Transactional
    public void deleteFarm(Integer id) {
        var farm = getById(id);
        farmValidation.validateFarmDeletion(farm);
        farm.setDeletedAt(Instant.now());
        farmRepository.save(farm);
    }

    public List getAuditedFarmList(Integer farmId) {
        AuditReader reader = AuditReaderFactory.get(managerFactory.createEntityManager());
        AuditQuery queryHistoryOfFarmWithRev = reader.createQuery()
                .forRevisionsOfEntity(BreedingAnimalFarmEntity.class, false, false)
                .add(AuditEntity.property("id").eq(farmId));
        return queryHistoryOfFarmWithRev.getResultList();

    }

    public void checkFarmByAreaAndSpecialization(Integer areaId, Integer specializationId) {
        var specialization = getFarmSpecialization(specializationId);
        farmValidation.existsByAdministrativeAreaIdAndFarmSpecializationIdAndFarmerPin(areaId, specialization);
    }

    private BreedingAnimalFarmSpecializationEntity getFarmSpecialization(Integer specializationId) {
        var specialization = farmSpecializationRepository.findById(specializationId)
                .orElseThrow(() -> new BusinessException("Təsərrüfat istiqaməti mövcud deyil."));
        return specialization;
    }

    public boolean existsFarmByFarmId(Integer farmId) {
        return farmRepository.existsByIdAndFarmerAccountIdAndDeletedAtIsNull(farmId, farmerAccountManager.findFarmerAccount().getId());
    }

}
