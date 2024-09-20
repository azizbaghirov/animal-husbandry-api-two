package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmTypeDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmTypeEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BreedingAnimalFarmTypeModelDTOMapperConfig {

    @Bean
    public Mapper<BreedingAnimalFarmTypeEntity, BreedingAnimalFarmTypeDTO> breedingAnimalFarmTypeToDtoModelMapper() {
        return Mapping.from(BreedingAnimalFarmTypeEntity.class)
                .to(BreedingAnimalFarmTypeDTO.class)
                .omitOthers()
                .mapper();
    }
}
