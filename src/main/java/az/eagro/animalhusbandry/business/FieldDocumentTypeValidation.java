package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.api.service.model.DocumentRequestDTO;
import az.eagro.animalhusbandry.model.FieldDocumentTypeLabel;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldDocumentTypeValidation {

    public void validateFieldDocumentType(DocumentRequestDTO document) {
        if (!(Objects.equals(document.getReferencedDocumentTypeLabel(), FieldDocumentTypeLabel.EXTRACT_ACT.getLabel())
                || Objects.equals(document.getReferencedDocumentTypeLabel(), FieldDocumentTypeLabel.STATE_ACT.getLabel()))) {
            throw new BusinessException(FieldDocumentTypeLabel.RENT_ACT.getName() + "ynə uyğun predmet seçilməlidirx.");
        }
    }

}
