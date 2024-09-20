package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.ApplicationInitialMonitoringDTO;
import az.eagro.animalhusbandry.api.service.model.CertificationApplicationDataDTO;
import az.eagro.animalhusbandry.api.service.model.FieldValueDTO;
import az.eagro.animalhusbandry.model.CertificationApplicationDataEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationDataModelDTOMapperConfig {

    private final Mapper<FieldValueEntity, FieldValueDTO> fieldValueToDtoMapper;
    private final Mapper<CertificationApplicationEntity, ApplicationInitialMonitoringDTO> applicationInitialMonitoringModelDTOMapper;

    @Bean
    public Mapper<CertificationApplicationDataEntity, CertificationApplicationDataDTO> certificationApplicationDataToDtoMapper() {
        return Mapping.from(CertificationApplicationDataEntity.class)
                .to(CertificationApplicationDataDTO.class)
                .set(CertificationApplicationDataDTO::getFarmTypeId)
                .with(entity -> entity.getApplication().getFarmType().getId())
                .set(CertificationApplicationDataDTO::getAnimalTypeId)
                .with(entity -> entity.getApplication().getFarm().getAnimalType().getId())
                .useMapper(applicationInitialMonitoringModelDTOMapper)
                .useMapper(fieldValueToDtoMapper)
                .omitOthers()
                .mapper();
    }

}
