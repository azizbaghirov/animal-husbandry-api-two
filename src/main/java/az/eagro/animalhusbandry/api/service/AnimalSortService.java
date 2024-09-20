package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.AnimalSortDTO;
import az.eagro.animalhusbandry.business.AnimalSortManager;
import az.eagro.animalhusbandry.model.AnimalSortEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalSortService {

    private final Mapper<AnimalSortEntity, AnimalSortDTO> animalSortModelDTOMapper;
    private final AnimalSortManager animalSortManager;

    public List<AnimalSortDTO> getAnimalSortsByAnimalTypeId(Integer animalTypeId) {
        return animalSortModelDTOMapper.map(animalSortManager.getAnimalSortsByAnimalTypeId(animalTypeId));
    }

}
