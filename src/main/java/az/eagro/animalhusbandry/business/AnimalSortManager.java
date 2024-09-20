package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.AnimalSortEntity;
import az.eagro.animalhusbandry.repository.AnimalSortRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalSortManager {

    private final AnimalSortRepository animalSortRepository;

    public List<AnimalSortEntity> getAnimalSortsByAnimalTypeId(Integer animalTypeId) {
        return animalSortRepository.getAnimalSortEntitiesByAnimalTypeIdAndActiveIsTrue(animalTypeId);
    }
}
