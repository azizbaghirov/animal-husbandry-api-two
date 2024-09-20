package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.DiscoveredDataDTO;
import az.eagro.animalhusbandry.api.service.model.FieldValueDTO;
import az.eagro.animalhusbandry.api.service.model.NewDiscoveredDataDTO;
import az.eagro.animalhusbandry.api.service.model.NewFieldValueDTO;
import az.eagro.animalhusbandry.model.DiscoveredDataEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DiscoveredDataModelDTOMapperConfig {

    private final Mapper<NewFieldValueDTO, FieldValueEntity> newFieldValueModelMapper;
    private final Mapper<FieldValueEntity, FieldValueDTO> fieldValueToDtoMapper;

    @Bean
    public Mapper<NewDiscoveredDataDTO, DiscoveredDataEntity> newDiscoveredDataModelMapper() {
        return Mapping.from(NewDiscoveredDataDTO.class)
                .to(DiscoveredDataEntity.class)
                .set(DiscoveredDataEntity::getDeclaredValue)
                .with(dto -> FieldValueEntity.builder().id(dto.getDeclaredValueId()).build())
                .useMapper(newFieldValueModelMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<DiscoveredDataEntity, DiscoveredDataDTO> discoveredDataToDtoMapper() {
        return Mapping.from(DiscoveredDataEntity.class)
                .to(DiscoveredDataDTO.class)
                .useMapper(fieldValueToDtoMapper)
                .omitOthers()
                .mapper();
    }

}
