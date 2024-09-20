package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AnimalTypeDTO;
import az.eagro.animalhusbandry.model.AnimalTypeEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalTypeModelDTOMapperConfig {

    @Bean
    public Mapper<AnimalTypeEntity, AnimalTypeDTO> animalTypeModelDTOMapper() {
        return Mapping.from(AnimalTypeEntity.class)
                .to(AnimalTypeDTO.class)
                .omitOthers()
                .mapper();
    }
}
