package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.LegalPersonDTO;
import az.eagro.animalhusbandry.model.LegalPersonEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegalPersonModelDTOMapperConfig {

    @Bean
    public Mapper<LegalPersonEntity, LegalPersonDTO> legalPersonToDtoMapper() {
        return Mapping.from(LegalPersonEntity.class)
                .to(LegalPersonDTO.class)
                .omitOthers()
                .mapper();
    }

}
