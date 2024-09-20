package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AssetInputConfigurationDTO;
import az.eagro.animalhusbandry.model.AssetInputConfigurationEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssetInputConfigurationModelDTOMapperConfig {

    @Bean
    public Mapper<AssetInputConfigurationEntity, AssetInputConfigurationDTO> assetInputConfigurationEntityToDtoMapper() {
        return Mapping.from(AssetInputConfigurationEntity.class)
                .to(AssetInputConfigurationDTO.class)
                .omitInSource(AssetInputConfigurationEntity::getAsset)
                .omitInSource(AssetInputConfigurationEntity::getFarmType)
                .omitInSource(AssetInputConfigurationEntity::getFarmSpecialization)
                .set(AssetInputConfigurationDTO::getName)
                .with(n -> n.getAsset().getName())
                .set(AssetInputConfigurationDTO::getAssetId)
                .with(n -> n.getAsset().getId())
                .set(AssetInputConfigurationDTO::getDescription)
                .with(n -> n.getDescription() == null ? null : n.getDescription())
                .omitOthers()
                .mapper();
    }
}
