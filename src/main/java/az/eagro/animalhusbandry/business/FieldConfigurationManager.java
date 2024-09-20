package az.eagro.animalhusbandry.business;


import az.eagro.animalhusbandry.model.FieldConfigurationEntity;
import az.eagro.animalhusbandry.repository.FieldConfigurationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldConfigurationManager {

    private final FieldConfigurationRepository fieldConfigurationRepository;

    public FieldConfigurationEntity findFieldConfiguration(Integer farmTypeId, Integer specialisationId, Integer animalTypeId) {
        return fieldConfigurationRepository.findByFarmTypeAndFarmSpecializationAndAnimal(farmTypeId, specialisationId, animalTypeId)
                .orElseThrow(() -> new BusinessException("Sahə konfiqurasiyası tapılmadı. "));
    }
}
