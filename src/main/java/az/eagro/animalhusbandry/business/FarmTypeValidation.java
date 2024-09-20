package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.repository.FarmTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmTypeValidation {

    private final FarmTypeRepository farmTypeRepository;

    public void ensureHasFarmType(Integer id) {
        if (!farmTypeRepository.existsById(id)) {
            throw new BusinessException("Təsərrüfat növü mövcud deyil. ");
        }
    }

}
