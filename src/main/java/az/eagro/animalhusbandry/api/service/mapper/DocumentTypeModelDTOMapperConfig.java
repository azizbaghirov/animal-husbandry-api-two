package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.feign.model.DocumentTypeDTO;
import az.eagro.animalhusbandry.model.DocumentTypeEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentTypeModelDTOMapperConfig {

    @Bean
    public Mapper<DocumentTypeEntity, DocumentTypeDTO> documentTypeModelDTOMapper() {
        return Mapping.from(DocumentTypeEntity.class)
                .to(DocumentTypeDTO.class)
                .omitOthers()
                .mapper();

    }

}
