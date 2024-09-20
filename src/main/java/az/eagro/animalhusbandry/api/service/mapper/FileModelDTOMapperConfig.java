package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.certification.FileDTO;
import az.eagro.animalhusbandry.model.FileEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileModelDTOMapperConfig {

    @Bean
    public Mapper<FileEntity, FileDTO> fileToDtoMapper() {
        return Mapping.from(FileEntity.class)
                .to(FileDTO.class)
                .set(FileDTO::getDocumentType)
                .with(fileEntity -> fileEntity.getDocumentType().getName())
                .omitOthers()
                .mapper();
    }
}
