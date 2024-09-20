package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import az.eagro.animalhusbandry.model.RegionEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegionModelDTOMapperConfig {

    @Bean
    public Mapper<RegionDTO, RegionEntity> newRegionModelMapper() {
        return Mapping.from(RegionDTO.class)
                .to(RegionEntity.class)
                .mapper();
    }

    @Bean
    public Mapper<RegionEntity, RegionDTO> regionModelDTOMapper() {
        return Mapping.from(RegionEntity.class)
                .to(RegionDTO.class)
                .mapper();
    }

}
