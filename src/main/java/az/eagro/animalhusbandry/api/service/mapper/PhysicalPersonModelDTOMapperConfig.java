package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.PhysicalPersonDTO;
import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhysicalPersonModelDTOMapperConfig {

    @Bean
    public Mapper<PhysicalPersonEntity, PhysicalPersonDTO> physicalPersonToDtoMapper() {
        return Mapping.from(PhysicalPersonEntity.class)
                .to(PhysicalPersonDTO.class)
                .omitOthers()
                .mapper();
    }

}
