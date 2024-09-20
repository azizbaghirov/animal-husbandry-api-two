package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AnimalSortDTO;
import az.eagro.animalhusbandry.model.AnimalSortEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalSortModelDTOMapperConfig {

    @Bean
    public Mapper<AnimalSortEntity, AnimalSortDTO> animalSortModelDTOMapper() {
        return Mapping.from(AnimalSortEntity.class)
                .to(AnimalSortDTO.class)
                .omitOthers()
                .mapper();
    }
}
