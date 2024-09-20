package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.VaccinationDTO;
import az.eagro.animalhusbandry.model.VaccinationEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaccinationModelDTOMapperConfig {

    @Bean
    public Mapper<VaccinationEntity, VaccinationDTO> vaccinationModelDTOMapper() {
        return Mapping.from(VaccinationEntity.class)
                .to(VaccinationDTO.class)
                .omitOthers()
                .mapper();
    }

}
