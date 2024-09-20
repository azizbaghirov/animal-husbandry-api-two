package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FieldDTO;
import az.eagro.animalhusbandry.api.service.model.FieldSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.FieldValidationRuleDTO;
import az.eagro.animalhusbandry.api.service.model.MeasurementUnitDTO;
import az.eagro.animalhusbandry.model.FieldEntity;
import az.eagro.animalhusbandry.model.FieldValidationRuleEntity;
import az.eagro.animalhusbandry.model.MeasurementUnitEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldModelDTOMapperConfig {

    private final Mapper<MeasurementUnitEntity, MeasurementUnitDTO> measurementUnitToDtoMapper;
    private final Mapper<FieldValidationRuleEntity, FieldValidationRuleDTO> fieldValidationRuleToDtoMapper;

    @Bean
    public Mapper<FieldEntity, FieldDTO> fieldToDtoMapper() {
        return Mapping.from(FieldEntity.class)
                .to(FieldDTO.class)
                .useMapper(measurementUnitToDtoMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<FieldEntity, FieldSummaryDTO> fieldSummaryToDtoMapper() {
        return Mapping.from(FieldEntity.class)
                .to(FieldSummaryDTO.class)
                .useMapper(fieldValidationRuleToDtoMapper)
                .useMapper(measurementUnitToDtoMapper)
                .omitOthers()
                .mapper();
    }

}
