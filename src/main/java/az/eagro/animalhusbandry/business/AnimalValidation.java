package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.repository.AnimalSortRepository;
import az.eagro.animalhusbandry.repository.AnimalTypeRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalValidation {

    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalSortRepository animalSortRepository;

    void ensureExistsAnimal(String animalSortId, Integer animalTypeId, Integer farmTypeId) {
        if (!animalSortRepository.existsById(animalSortId)) {
            throw new BusinessException("Heyvan cinsi mövcud deyil");
        }
        if (!animalTypeRepository.existsById(animalTypeId)) {
            throw new BusinessException("Heyvan tipi mövcud deyil");
        }
        if (!animalSortRepository.existsByIdAndAnimalTypeIdAndAnimalTypeFarmTypeId(animalSortId, animalTypeId, farmTypeId)) {
            throw new BusinessException("Heyvan tipi, heyvan cinsi və təsərrüfat tipi uyğun deyil");
        }
    }

    public void checkBxmAnimals(Set<Integer> result, Set<Integer> markedIds) {
        if (!result.isEmpty() && markedIds.isEmpty()) {
            throw new BusinessException("Fermerə aid heyvanlardan ən azı 1-i seçilməlidir");
        }
        if (!result.containsAll(markedIds)) {
            throw new BusinessException("Mövcud heyvan siyahsı ilə seçilmiş heyvan siyahısı uyğun deyil. ");
        }
    }

}
