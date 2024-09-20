package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FarmerAccountDTO;
import az.eagro.animalhusbandry.api.service.model.LegalPersonDTO;
import az.eagro.animalhusbandry.api.service.model.PhysicalPersonDTO;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.LegalPersonEntity;
import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmerAccountModelDTOMapperConfig {

    private final Mapper<LegalPersonEntity, LegalPersonDTO> legalPersonToDtoMapper;
    private final Mapper<PhysicalPersonEntity, PhysicalPersonDTO> physicalPersonToDtoMapper;

    @Bean
    public Mapper<FarmerAccountEntity, FarmerAccountDTO> farmerAccountToDtoMapper() {
        return Mapping.from(FarmerAccountEntity.class)
                .to(FarmerAccountDTO.class)
                .useMapper(legalPersonToDtoMapper)
                .useMapper(physicalPersonToDtoMapper)
                .omitOthers()
                .mapper();
    }

}
