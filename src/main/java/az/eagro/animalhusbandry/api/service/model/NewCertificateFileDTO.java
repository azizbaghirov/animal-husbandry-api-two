package az.eagro.animalhusbandry.api.service.model;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCertificateFileDTO {

    @NotNull(message = "File kontent daxil edilməyib")
    private String content;
    @NotNull(message = "Content-Type daxil edilməyib")
    private String contentType;
    @NotNull(message = "OriginalFileName daxil edilməyib")
    private String originalFilename;

}
