package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FieldAssetDTO;
import az.eagro.animalhusbandry.model.FieldEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldAssetModelDTOMapperConfig {

    @Bean
    public Mapper<FieldEntity, FieldAssetDTO> fieldAssetEntityToDtoMapper() {
        return Mapping.from(FieldEntity.class)
                .to(FieldAssetDTO.class)
                .set(FieldAssetDTO::getAssetId)
                .with(fieldEntity -> fieldEntity.getId())
                .omitOthers()
                .mapper();
    }
}
