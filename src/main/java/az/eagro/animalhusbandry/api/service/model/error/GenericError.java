package az.eagro.animalhusbandry.api.service.model.error;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GenericError {

    private String errorMessage;

}
