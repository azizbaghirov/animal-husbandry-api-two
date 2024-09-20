package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.FieldAssetDTO;
import az.eagro.animalhusbandry.api.service.model.FieldDTO;
import az.eagro.animalhusbandry.api.service.model.FieldSummaryDTO;
import az.eagro.animalhusbandry.business.FieldManager;
import az.eagro.animalhusbandry.model.FieldEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldService {

    private final Mapper<FieldEntity, FieldSummaryDTO> fieldSummaryToDtoMapper;
    private final Mapper<FieldEntity, FieldAssetDTO> fieldAssetEntityToDtoMapper;
    private final FieldManager fieldManager;

    public List<FieldAssetDTO> getAssetsByFarmTypeIdAndFarmSpecializationId(Integer farmTypeId, Integer farmSpecializationId) {
        return fieldAssetEntityToDtoMapper
                .map(fieldManager.findAssetsByFarmTypeAndFarmSpecialization(farmTypeId, farmSpecializationId));
    }

    public List<FieldSummaryDTO> getFieldsByFarmId(Integer farmId) {
        return fieldSummaryToDtoMapper.map(fieldManager.getFieldsByFarmId(farmId));
    }

}
