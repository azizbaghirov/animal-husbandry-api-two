package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.AnimalTypeDTO;
import az.eagro.animalhusbandry.business.AnimalTypeManager;
import az.eagro.animalhusbandry.model.AnimalTypeEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalTypeService {

    private final Mapper<AnimalTypeEntity, AnimalTypeDTO> animalTypeModelDTOMapper;
    private final AnimalTypeManager animalTypeManager;

    public List<AnimalTypeDTO> getAnimalTypesByFarmTypeId(Integer farmTypeId) {
        return animalTypeModelDTOMapper.map(animalTypeManager.getAnimalTypesByFarmTypeId(farmTypeId));
    }
}
