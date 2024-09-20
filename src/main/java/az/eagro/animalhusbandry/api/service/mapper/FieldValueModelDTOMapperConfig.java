package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FieldDTO;
import az.eagro.animalhusbandry.api.service.model.FieldValueDTO;
import az.eagro.animalhusbandry.api.service.model.NewFieldValueDTO;
import az.eagro.animalhusbandry.model.FieldEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldValueModelDTOMapperConfig {

    private final Mapper<FieldEntity, FieldDTO> fieldToDtoMapper;

    @Bean
    public Mapper<FieldValueEntity, FieldValueDTO> fieldValueToDtoMapper() {
        return Mapping.from(FieldValueEntity.class)
                .to(FieldValueDTO.class)
                .useMapper(fieldToDtoMapper)
                .omitOthers()
                .mapper();
    }


    @Bean
    public Mapper<NewFieldValueDTO, FieldValueEntity> newFieldValueModelMapper() {
        return Mapping.from(NewFieldValueDTO.class)
                .to(FieldValueEntity.class)
                .set(FieldValueEntity::getField)
                .with(dto -> FieldEntity.builder().id(dto.getFieldId()).build())
                .omitOthers()
                .mapper();
    }

}
