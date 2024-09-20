package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldDocumentValidation {

    private final FieldDocumentManager documentManager;

    public void ensureExistsDocument(Set<Integer> documentIds, Integer areaId) {
        var externalIds = documentManager.getFieldDocuments(areaId, null).stream()
                .map(FieldDocumentDTO::getFieldId).collect(Collectors.toSet());

        if (!externalIds.containsAll(documentIds)) {
            throw new BusinessException("Sənədlər 'Ektis'-lə uyğun deyil. ");
        }
    }

}
