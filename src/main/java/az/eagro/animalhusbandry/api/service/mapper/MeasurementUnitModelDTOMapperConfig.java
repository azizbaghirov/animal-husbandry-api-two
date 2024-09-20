package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.MeasurementUnitDTO;
import az.eagro.animalhusbandry.model.MeasurementUnitEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeasurementUnitModelDTOMapperConfig {

    @Bean
    public Mapper<MeasurementUnitEntity, MeasurementUnitDTO> measurementUnitToDtoMapper() {
        return Mapping.from(MeasurementUnitEntity.class)
                .to(MeasurementUnitDTO.class)
                .omitOthers()
                .mapper();
    }

}
