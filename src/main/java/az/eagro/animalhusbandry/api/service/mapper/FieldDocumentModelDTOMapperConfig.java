package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.FieldDocumentApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.FieldDocumentSummaryDTO;
import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import az.eagro.animalhusbandry.model.FieldDocumentEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldDocumentModelDTOMapperConfig {

    @Bean
    public Mapper<FieldDocumentDTO, FieldDocumentEntity> newFieldDocumentModelDTOMapper() {
        return Mapping.from(FieldDocumentDTO.class)
                .to(FieldDocumentEntity.class)
                .set(FieldDocumentEntity::getFieldName)
                .with(FieldDocumentDTO::getName)
                .set(FieldDocumentEntity::getDocumentId)
                .with(dto -> dto.getDocument().getDocumentId())
                .set(FieldDocumentEntity::getJournalNumber)
                .with(dto -> dto.getDocument().getJournalNumber())
                .set(FieldDocumentEntity::getRegionCode)
                .with(dto -> dto.getDocument().getRegionCode())
                .set(FieldDocumentEntity::getRegistryNumber)
                .with(dto -> dto.getDocument().getRegistryNumber())
                .set(FieldDocumentEntity::getRegistrationNumber)
                .with(dto -> dto.getDocument().getRegistrationNumber())
                .set(FieldDocumentEntity::getContractNumber)
                .with(dto -> dto.getDocument().getContractNumber())
                .set(FieldDocumentEntity::getDocumentTypeName)
                .with(dto -> dto.getDocument().getDocumentType().getName())
                .set(FieldDocumentEntity::getDocumentTypeLabel)
                .with(dto -> dto.getDocument().getDocumentType().getLabel())

                .set(FieldDocumentEntity::getReferencedDocumentId)
                .with(dto ->
                        dto.getDocument().getReferencedDocument() == null ? null : dto.getDocument().getReferencedDocument().getId())

                .set(FieldDocumentEntity::getReferencedSpaceHa)
                .with(dto ->
                        dto.getDocument().getReferencedDocument() == null ? null : dto.getDocument().getReferencedDocument().getSpaceHa())

                .set(FieldDocumentEntity::getReferencedRegionCode)
                .with(dto ->
                        dto.getDocument().getReferencedDocument() == null ? null : dto.getDocument().getReferencedDocument().getRegionCode())

                .set(FieldDocumentEntity::getReferencedJournalNumber)
                .with(dto ->
                        dto.getDocument().getReferencedDocument() == null ? null : dto.getDocument().getReferencedDocument().getJournalNumber())

                .set(FieldDocumentEntity::getReferencedRegistryNumber)
                .with(dto ->
                        dto.getDocument().getReferencedDocument() == null ? null : dto.getDocument().getReferencedDocument().getRegistryNumber())

                .set(FieldDocumentEntity::getReferencedRegistrationNumber)
                .with(dto ->
                        dto.getDocument().getReferencedDocument() == null ? null : dto.getDocument().getReferencedDocument().getRegistrationNumber())

                .set(FieldDocumentEntity::getReferencedDocumentTypeName)
                .with(dto ->
                        (dto.getDocument().getReferencedDocument() != null
                                && dto.getDocument().getReferencedDocument().getDocumentType() != null)
                                ? dto.getDocument().getReferencedDocument().getDocumentType().getName()
                                : null)

                .set(FieldDocumentEntity::getReferencedDocumentTypeLabel)
                .with(dto ->
                        (dto.getDocument().getReferencedDocument() != null
                                && dto.getDocument().getReferencedDocument().getDocumentType() != null)
                                ? dto.getDocument().getReferencedDocument().getDocumentType().getLabel()
                                : null)


                .set(FieldDocumentEntity::getDocumentClassificationName)
                .with(dto ->
                        dto.getDocument().getDocumentClassification() == null ? null : dto.getDocument().getDocumentClassification().getName())

                .set(FieldDocumentEntity::getDocumentClassificationLabel)
                .with(dto ->
                        dto.getDocument().getDocumentClassification() == null ? null : dto.getDocument().getDocumentClassification().getLabel())

                .set(FieldDocumentEntity::getContourId)
                .with(dto ->
                        dto.getContours().isEmpty() ? null : dto.getContours().get(0).getId())

                .set(FieldDocumentEntity::getContourName)
                .with(dto ->
                        dto.getContours().isEmpty() ? null : dto.getContours().get(0).getName())

                .set(FieldDocumentEntity::getContourSpaceHa)
                .with(dto ->
                        dto.getContours().isEmpty() ? null : dto.getContours().get(0).getSpaceHa())

                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<FieldDocumentEntity, FieldDocumentSummaryDTO> fieldDocumentSummaryModelDTOMapper() {
        return Mapping.from(FieldDocumentEntity.class)
                .to(FieldDocumentSummaryDTO.class)
                .omitOthers()
                .mapper();

    }

    @Bean
    public Mapper<FieldDocumentEntity, FieldDocumentApplicationDTO> fieldDocumentApplicationModelDTOMapper() {
        return Mapping.from(FieldDocumentEntity.class)
                .to(FieldDocumentApplicationDTO.class)
                .omitOthers()
                .mapper();

    }
}
