package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.FarmSpecializationDTO;
import az.eagro.animalhusbandry.business.BreedingAnimalFarmSpecializationManager;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmSpecializationService {

    private final BreedingAnimalFarmSpecializationManager farmSpecializationManager;
    private final Mapper<BreedingAnimalFarmSpecializationEntity, FarmSpecializationDTO> farmSpecializationModelDTOMapper;

    public List<FarmSpecializationDTO> getFarmSpecializationByFarmTypeIdAndAnimalTypeId(Integer farmTypeId, Integer animalTypeId) {
        return farmSpecializationModelDTOMapper
                .map(farmSpecializationManager.getFarmSpecializationsByFarmTypeIdAndAnimalTypeId(farmTypeId, animalTypeId));
    }

    public boolean ensureExistsByFarmTypeId(Integer farmTypeId) {
        return farmSpecializationManager.ensureExistsByFarmTypeId(farmTypeId);

    }

}
