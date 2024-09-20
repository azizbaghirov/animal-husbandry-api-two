package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AssetDTO;
import az.eagro.animalhusbandry.model.AssetEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssetModelDTOMapperConfig {

    @Bean
    public Mapper<AssetEntity, AssetDTO> assetToDtoModelMapper() {
        return Mapping.from(AssetEntity.class)
                .to(AssetDTO.class)
                .omitOthers()
                .mapper();
    }
}
