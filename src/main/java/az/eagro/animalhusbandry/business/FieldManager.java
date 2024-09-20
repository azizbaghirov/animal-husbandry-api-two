package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.FieldConfigurationEntity;
import az.eagro.animalhusbandry.model.FieldEntity;
import az.eagro.animalhusbandry.model.FieldType;
import az.eagro.animalhusbandry.repository.FieldRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldManager {

    private final FieldRepository fieldRepository;
    private final BreedingAnimalFarmManager farmManager;
    private final FieldConfigurationManager fieldConfigurationManager;

    public List<FieldEntity> findAssetsByFarmTypeAndFarmSpecialization(Integer farmTypeId, Integer farmSpecializationId) {
        return fieldRepository.findAllAssetsByFarmTypeIdAndFarmSpecializationId(FieldType.ASSET, farmTypeId, farmSpecializationId);
    }

    public List<FieldEntity> getFieldsByFarmId(Integer farmId) {

        BreedingAnimalFarmEntity farm = farmManager.getById(farmId);
        Integer specialisationId = farm.getFarmSpecialization() == null ? null : farm.getFarmSpecialization().getId();
        Integer animalTypeId = farm.getAnimalType().getId();
        Integer farmTypeId = farm.getFarmType().getId();

        FieldConfigurationEntity fieldConfiguration = fieldConfigurationManager.findFieldConfiguration(farmTypeId, specialisationId, animalTypeId);

        return fieldRepository.findAllByFieldConfigurationId(fieldConfiguration.getId());
    }

}
