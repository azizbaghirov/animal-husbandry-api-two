package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.PageResponse;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmDTO;
import az.eagro.animalhusbandry.api.service.model.FarmAuditDTO;
import az.eagro.animalhusbandry.api.service.model.FindBreedingAnimalFarmDTO;
import az.eagro.animalhusbandry.api.service.model.NewFarmDTO;
import az.eagro.animalhusbandry.business.BreedingAnimalFarmManager;
import az.eagro.animalhusbandry.business.FieldDocumentManager;
import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.FieldDocumentEntity;
import az.eagro.animalhusbandry.model.RevisionInfoEntity;
import com.remondis.remap.Mapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionType;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BreedingAnimalFarmService {

    private final Mapper<NewFarmDTO, BreedingAnimalFarmEntity> newFarmModelMapper;
    private final Mapper<BreedingAnimalFarmEntity, BreedingAnimalFarmDTO> farmToDtoMapper;
    private final BreedingAnimalFarmManager farmManager;
    private final FieldDocumentManager fieldDocumentManager;
    private final Mapper<FieldDocumentDTO, FieldDocumentEntity> newFieldDocumentModelDTOMapper;

    public void createFarm(NewFarmDTO farmDTO) {
        var newFarm = newFarmModelMapper.map(farmDTO);
        var fieldDocuments = newFieldDocumentModelDTOMapper.map(fieldDocumentManager.findFieldDocuments(
                farmDTO.getActIds(), farmDTO.getAdministrativeAreaId(), farmDTO.getDocumentType(), farmDTO.getContourId()));
        newFarm.setFieldDocuments(fieldDocuments);
        farmManager.createFarm(newFarm);
    }

    public PageResponse<BreedingAnimalFarmDTO> getFarmListByRegionIdAndFarmTypeId(FindBreedingAnimalFarmDTO farmDTO) {
        Pageable pageable = PageRequest.of(farmDTO.getPage(), farmDTO.getSize());
        var farms = farmManager.getFarmListByRegionIdAndFarmTypeId(farmDTO, pageable);
        return new PageResponse<>(farmToDtoMapper.map(farms.getContent()), farms.getTotalPages(), farms.getTotalElements());
    }

    public void deleteFarm(Integer id) {
        farmManager.deleteFarm(id);
    }

    public List<FarmAuditDTO> getAuditedFarms(Integer farmId) {
        List<FarmAuditDTO> list = new ArrayList<>();
        List lstHistoryOfFarmWithRev = farmManager.getAuditedFarmList(farmId);
        for (Object item : lstHistoryOfFarmWithRev) {
            FarmAuditDTO dto = new FarmAuditDTO();
            BreedingAnimalFarmEntity farm = (BreedingAnimalFarmEntity) ((Object[]) item)[0];
            RevisionInfoEntity revisionEntity = (RevisionInfoEntity) ((Object[]) item)[1];
            RevisionType revisionType = (RevisionType) ((Object[]) item)[2];
            dto.setRevisonDate(revisionEntity.getRevisionTimestamp().toInstant());
            dto.setRevisionType(revisionType.name());
            list.add(dto);
        }
        return list;
    }

    public BreedingAnimalFarmDTO getById(Integer id) {
        return farmToDtoMapper.map(farmManager.getByIdAndFarmer(id));
    }

    public void checkFarmByAreaAndSpecialization(Integer areaId, Integer farmSpecializationId) {
        farmManager.checkFarmByAreaAndSpecialization(areaId, farmSpecializationId);
    }

    public boolean existsFarmByFarmId(Integer farmId) {
        return farmManager.existsFarmByFarmId(farmId);
    }

}
