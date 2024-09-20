package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.repository.BreedingAnimalFarmRepository;
import az.eagro.animalhusbandry.repository.BreedingAnimalFarmSpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BreedingAnimalFarmValidation {

    private final BreedingAnimalFarmRepository farmRepository;
    private final BreedingAnimalFarmSpecializationRepository farmSpecializationRepository;
    private final FarmerAccountManager farmerAccountManager;

    void ensureNewFarmIsValid(BreedingAnimalFarmEntity newFarm) {

        var farmer = farmerAccountManager.findFarmerAccount();
        if (newFarm.getFarmSpecialization() != null) {

            var spec = farmSpecializationRepository.findById(newFarm.getFarmSpecialization().getId())
                    .orElseThrow(() -> new BusinessException("Təsərrüfat istiqaməti mövcud deyil."));

            farmSpecializationRepository.findByIdAndFarmTypeId(newFarm.getFarmSpecialization().getId(), newFarm.getFarmType().getId())
                    .stream().findFirst()
                    .orElseThrow(() -> new BusinessException("Təsərrüfat istiqaməti seçilmiş təsərrüfat növünə uyğun deyil."));

            if ((farmRepository
                    .existsByFarmTypeIdAndFarmSpecializationIdAndAdministrativeAreaIdAndAnimalTypeAndAnimalSortAndFarmerAccountIdAndDeletedAtIsNull(
                    newFarm.getFarmType().getId(), newFarm.getFarmSpecialization().getId(), newFarm.getAdministrativeArea().getId(),
                    newFarm.getAnimalType(), newFarm.getAnimalSort(), farmer.getId()))) {
                throw new BusinessException("Seçilmiş inzibati ərazidə " + spec.getName() + " istiqaməti üzrə damazlıq təsərrüfat mövcuddur.");
            }
        } else {
            if (farmRepository.existsByFarmTypeIdAndAdministrativeAreaIdAndAnimalTypeAndAnimalSortAndFarmerAccountIdAndDeletedAtIsNull(
                    newFarm.getFarmType().getId(),
                    newFarm.getAdministrativeArea().getId(), newFarm.getAnimalType(), newFarm.getAnimalSort(), farmer.getId())) {
                throw new BusinessException("İsitfadəçinin təsərrüfatı mövcuddur.");
            }
        }
    }

    public void validateCurrentUserIsOwnerOfFarm(BreedingAnimalFarmEntity farm) {
        if (!farmerAccountManager.findFarmerAccount().getId().equals(farm.getFarmerAccount().getId())) {
            throw new BusinessException("Təsərrüfat istifadəçiyə aid deyil.");
        }
    }

    public void validateFarmDeletion(BreedingAnimalFarmEntity farm) {
        validateCurrentUserIsOwnerOfFarm(farm);
        if (!farm.getDeletable()) {
            throw new BusinessException("Təsərrüfat silinə bilməz. ");
        }
        if (farm.getDeletedAt() != null) {
            throw new BusinessException("Təsərrüfat daha əvvəl silinib. ");
        }
    }

    public void existsByAdministrativeAreaIdAndFarmSpecializationIdAndFarmerPin(Integer areaId, BreedingAnimalFarmSpecializationEntity spec) {
        if (farmRepository.existsByAdministrativeAreaIdAndFarmSpecializationIdAndFarmerAccountIdAndDeletedAtIsNull(
                areaId, spec.getId(), farmerAccountManager.findFarmerAccount().getId())) {
            throw new BusinessException("Seçilmiş inzibati ərazidə '" + spec.getName()
                    + "' təsərrüfat istiqaməti üzrə damazlıq təsərrüfat mövcuddur.");
        }
    }

    public void ensureExistsAppropriateApplicationForFarm(CertificationApplicationEntity application) {
        var farm = farmRepository.findByIdAndFarmerAccountId(application.getFarm().getId(), farmerAccountManager.findFarmerAccount().getId())
                .orElseThrow(() -> new BusinessException("İstifadəçi müraciətinə uyğun təsərrüfat tapılmadı."));
        farm.setDeletable(false);
        application.setFarm(farm);

    }

}
