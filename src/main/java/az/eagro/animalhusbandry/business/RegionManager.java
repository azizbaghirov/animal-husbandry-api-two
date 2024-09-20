package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.RegionEntity;
import az.eagro.animalhusbandry.repository.RegionRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RegionManager {

    private final RegionRepository regionRepository;

    public RegionEntity getById(Integer id) {
        return regionRepository.findById(id).get();
    }

    public Optional<RegionEntity> findById(Integer id) {
        return regionRepository.findById(id);
    }

    @Transactional
    public RegionEntity saveRegion(RegionEntity region) {
        return regionRepository.save(region);
    }

    public List<RegionEntity> getRegions() {
        return regionRepository.findAll();
    }

}
