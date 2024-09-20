package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.GrantedAuthorityDTO;
import az.eagro.animalhusbandry.model.GrantedAuthorityEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrantedAuthorityDTOMapperConfig {

    @Bean
    public Mapper<GrantedAuthorityEntity, GrantedAuthorityDTO> grantedAuthorityModelDTOMapper() {
        return Mapping.from(GrantedAuthorityEntity.class)
                .to(GrantedAuthorityDTO.class)
                .omitOthers()
                .mapper();
    }
}
