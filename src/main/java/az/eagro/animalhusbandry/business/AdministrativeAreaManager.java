package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.AdministrativeAreaEntity;
import az.eagro.animalhusbandry.repository.AdministrativeAreaRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AdministrativeAreaManager {

    private final AdministrativeAreaRepository administrativeAreaRepository;

    public AdministrativeAreaEntity getById(Integer id) {
        return administrativeAreaRepository.findById(id).get();
    }

    public Optional<AdministrativeAreaEntity> findById(Integer id) {
        return administrativeAreaRepository.findById(id);
    }

    @Transactional
    public void saveAdministrativeArea(AdministrativeAreaEntity entity) {
        administrativeAreaRepository.save(entity);
    }
}
