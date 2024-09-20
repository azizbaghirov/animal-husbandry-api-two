package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.business.DocumentTypeManager;
import az.eagro.animalhusbandry.feign.model.DocumentTypeDTO;
import az.eagro.animalhusbandry.model.DocumentTypeEntity;
import az.eagro.animalhusbandry.model.DocumentTypeLabel;
import com.remondis.remap.Mapper;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DocumentTypeService {

    private final DocumentTypeManager documentTypeManager;
    private final Mapper<DocumentTypeEntity, DocumentTypeDTO> documentTypeModelDTOMapper;

    public List<DocumentTypeDTO> getInitialMonitoringDocumentTypes() {
        return documentTypeModelDTOMapper.map(documentTypeManager.getDocumentTypesByLabels(Arrays.asList(DocumentTypeLabel.ACT.name())));
    }

    public List<DocumentTypeDTO> getFinalMonitoringDocumentTypes() {
        List<String> documentTypes = Arrays.asList(DocumentTypeLabel.ACT.name(), DocumentTypeLabel.PROTOCOL.name());
        return documentTypeModelDTOMapper.map(documentTypeManager.getDocumentTypesByLabels(documentTypes));
    }

    public DocumentTypeEntity getDocumentTypeByLabel(String label) {
        return documentTypeManager.getDocumentTypesByLabels(Arrays.asList(label)).get(0);
    }

}
