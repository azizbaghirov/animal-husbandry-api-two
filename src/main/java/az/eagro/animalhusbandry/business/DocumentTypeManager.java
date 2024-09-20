package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.DocumentTypeEntity;
import az.eagro.animalhusbandry.repository.DocumentTypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DocumentTypeManager {

    private final DocumentTypeRepository documentTypeRepository;

    public List<DocumentTypeEntity> getDocumentTypesByLabels(List<String> labels) {
        return documentTypeRepository.findAllByLabelIn(labels).orElseThrow(() -> new BusinessException("Label not exists."));
    }

}