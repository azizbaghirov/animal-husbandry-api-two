package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FarmSpecializationDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FarmSpecializationModelDTOMapperConfig {

    @Bean
    public Mapper<BreedingAnimalFarmSpecializationEntity, FarmSpecializationDTO> farmSpecializationModelDTOMapper() {
        return Mapping.from(BreedingAnimalFarmSpecializationEntity.class)
                .to(FarmSpecializationDTO.class)
                .omitOthers()
                .mapper();
    }

}
