package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.api.service.model.DocumentRequestDTO;
import az.eagro.animalhusbandry.feign.EagroFeignClient;
import az.eagro.animalhusbandry.feign.model.ContourDTO;
import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import az.eagro.animalhusbandry.model.FieldDocumentEntity;
import az.eagro.animalhusbandry.model.FieldDocumentTypeLabel;
import az.eagro.animalhusbandry.repository.FieldDocumentRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldDocumentManager {

    private final EagroFeignClient eagroFeignClient;
    private final AuthenticatedUserInfoProvider auth;
    private final FarmerAccountManager farmer;
    private final FieldDocumentRepository fieldDocumentRepository;
    private final FieldDocumentTypeValidation fieldDocumentValidate;

    public Set<FieldDocumentDTO> getFieldDocumentsFromEktis(Integer areaId) {
        String taxNumber = auth.isLegal() ? auth.getLegalPerson().getTaxPayerIdentificationNumber() : null;
        Set<FieldDocumentDTO> fieldDocuments = eagroFeignClient.getActs(areaId, auth.getUserPin(), taxNumber);
        if (fieldDocuments.isEmpty()) {
            throw new BusinessException("Cari inzibati rayon daxilində heç bir ərazi qeydə alınmayıb.");
        }
        return fieldDocuments;
    }

    public Set<FieldDocumentDTO> getFieldDocumentsByAreaId(Integer areaId, DocumentRequestDTO document) {
        Set<FieldDocumentDTO> result = getFieldDocuments(areaId, document);
        if (result.isEmpty()) {
            throw new BusinessException("Sənəd siyahısı boş ola bilməz");
        }
        return result;

    }

    public Set<FieldDocumentDTO> getFieldDocuments(Integer areaId, DocumentRequestDTO document) {

        if (Objects.equals(document.getDocumentTypeLabel(), FieldDocumentTypeLabel.RENT_ACT.getLabel())) {
            fieldDocumentValidate.validateFieldDocumentType(document);
        }

        return getFieldDocumentsFromEktis(areaId).stream()
                .filter(fieldDocumentDTO ->
                        Objects.equals(document.getDocumentTypeLabel(), fieldDocumentDTO.getDocument().getDocumentType().getLabel()))
                .filter(fieldDocumentDTO -> {
                    String referencedDocumentTypeLabel = document.getReferencedDocumentTypeLabel();
                    if (Objects.equals(document.getDocumentTypeLabel(), FieldDocumentTypeLabel.RENT_ACT.getLabel())) {
                        return Objects.equals(
                                referencedDocumentTypeLabel, fieldDocumentDTO.getDocument().getReferencedDocument().getDocumentType().getLabel());
                    }
                    return true;
                }).collect(Collectors.toSet());
    }

    public Set<FieldDocumentDTO> findFieldDocuments(Set<Integer> documentIds, Integer areaId, DocumentRequestDTO documentType, Integer contourId) {
        Set<FieldDocumentDTO> result = getFieldDocuments(areaId, documentType).stream()
                .filter(fieldDocumentDTO -> documentIds.contains(fieldDocumentDTO.getFieldId()))
                .collect(Collectors.toSet());

        if (result.isEmpty()) {
            throw new BusinessException("Sənəd siyahısı boş ola biləz.");
        }

        if (isStateActOrRentActLinkedToStateAct(documentType)) {

            validateContourId(contourId);

            if (result.size() > 1) {
                throw new BusinessException(FieldDocumentTypeLabel.STATE_ACT.getName() + " sayı 1-dən çox ola bilməz.");
            }

            FieldDocumentDTO fieldDocument = result.iterator().next();

            List<ContourDTO> contours = fieldDocument.getContours();

            if (contours.isEmpty()) {
                throw new BusinessException("Kontur siyahısı boş ola bilməz.");
            }

            ContourDTO contour = contours.stream().filter(contourDTO -> Objects.equals(contourDTO.getId(), contourId))
                    .findFirst().orElseThrow(() -> new BusinessException("Kontur identifikasiya nömrəsi tapılmadı."));
            contours.clear();
            contours.add(contour);

            fieldDocument.setContours(contours);

            result.clear();
            result.add(fieldDocument);

        }
        return result;
    }

    private static boolean isStateActOrRentActLinkedToStateAct(DocumentRequestDTO documentType) {
        return Objects.equals(documentType.getDocumentTypeLabel(), FieldDocumentTypeLabel.STATE_ACT.getLabel())
                || (Objects.equals(documentType.getDocumentTypeLabel(), FieldDocumentTypeLabel.RENT_ACT.getLabel())
                && Objects.equals(documentType.getReferencedDocumentTypeLabel(), FieldDocumentTypeLabel.STATE_ACT.getLabel()));
    }

    private void validateContourId(Integer contourId) {
        if (contourId == null) {
            throw new BusinessException("Kontur identifikasiya nömrəsi vacib məlumatdır.");
        }
    }

    public List<FieldDocumentEntity> getFieldDocumentListByFarmId(Integer farmId) {
        var farmerAccount = farmer.findFarmerAccount();
        return fieldDocumentRepository.getByFarmId(farmId, farmerAccount);
    }

}
