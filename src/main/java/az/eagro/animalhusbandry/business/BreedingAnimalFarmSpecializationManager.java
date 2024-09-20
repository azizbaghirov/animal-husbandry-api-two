package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import az.eagro.animalhusbandry.repository.BreedingAnimalFarmSpecializationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BreedingAnimalFarmSpecializationManager {

    private final BreedingAnimalFarmSpecializationRepository farmSpecializationRepository;

    public List<BreedingAnimalFarmSpecializationEntity> getFarmSpecializationsByFarmTypeIdAndAnimalTypeId(Integer farmTypeId, Integer animalTypeId) {
        return farmSpecializationRepository.findByFarmTypeIdAndAnimalTypeId(farmTypeId, animalTypeId);
    }

    public boolean ensureExistsByFarmTypeId(Integer farmTypeId) {
        return farmSpecializationRepository.existsByFarmTypeId(farmTypeId);
    }
}
