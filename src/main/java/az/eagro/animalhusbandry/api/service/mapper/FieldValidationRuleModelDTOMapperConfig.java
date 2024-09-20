package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FieldValidationRuleDTO;
import az.eagro.animalhusbandry.api.service.model.MeasurementUnitDTO;
import az.eagro.animalhusbandry.model.FieldValidationRuleEntity;
import az.eagro.animalhusbandry.model.MeasurementUnitEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldValidationRuleModelDTOMapperConfig {

    @Bean
    public Mapper<FieldValidationRuleEntity, FieldValidationRuleDTO> fieldValidationRuleToDtoMapper() {
        return Mapping.from(FieldValidationRuleEntity.class)
                .to(FieldValidationRuleDTO.class)
                .omitOthers()
                .mapper();
    }

}
