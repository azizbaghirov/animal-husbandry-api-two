package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AdministrativeAreaDTO;
import az.eagro.animalhusbandry.model.AdministrativeAreaEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministrativeAreaModelDTOMapperConfig {

    @Bean
    public Mapper<AdministrativeAreaDTO, AdministrativeAreaEntity> newAdministrativeAreaModelMapper() {
        return Mapping.from(AdministrativeAreaDTO.class)
                .to(AdministrativeAreaEntity.class)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<AdministrativeAreaEntity, AdministrativeAreaDTO> administrativeAreaToDtoModelMapper() {
        return Mapping.from(AdministrativeAreaEntity.class)
                .to(AdministrativeAreaDTO.class)
                .omitOthers()
                .mapper();
    }
}
