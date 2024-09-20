package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.GrantedAuthorityDTO;
import az.eagro.animalhusbandry.api.service.model.OperatorDTO;
import az.eagro.animalhusbandry.api.service.model.OperatorSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import az.eagro.animalhusbandry.model.GrantedAuthorityEntity;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.RegionEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OperatorModelDTOMapperConfig {

    private final Mapper<GrantedAuthorityEntity, GrantedAuthorityDTO> grantedAuthorityModelDTOMapper;
    private final Mapper<RegionEntity, RegionDTO> regionModelDTOMapper;

    @Bean
    public Mapper<OperatorEntity, OperatorDTO> operatorToDtoMapper() {
        return Mapping.from(OperatorEntity.class)
                .to(OperatorDTO.class)
                .useMapper(grantedAuthorityModelDTOMapper)
                .useMapper(regionModelDTOMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<OperatorEntity, OperatorSummaryDTO> operatorSummaryToDtoMapper() {
        return Mapping.from(OperatorEntity.class)
                .to(OperatorSummaryDTO.class)
                .omitOthers()
                .mapper();
    }

}
