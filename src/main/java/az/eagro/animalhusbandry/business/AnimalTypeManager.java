package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.AnimalTypeEntity;
import az.eagro.animalhusbandry.repository.AnimalTypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalTypeManager {

    private final AnimalTypeRepository animalTypeRepository;

    public List<AnimalTypeEntity> getAnimalTypesByFarmTypeId(Integer farmTypeId) {
        return animalTypeRepository.getAnimalTypeEntitiesByFarmTypeId(farmTypeId);
    }

}
