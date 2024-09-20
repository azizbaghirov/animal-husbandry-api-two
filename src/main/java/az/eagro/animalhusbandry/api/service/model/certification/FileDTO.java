package az.eagro.animalhusbandry.api.service.model.certification;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileDTO {

    private UUID id;
    private String path;
    private String documentType;
    private String contentType;

}
