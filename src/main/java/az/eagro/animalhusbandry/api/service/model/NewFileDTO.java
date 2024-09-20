package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.feign.model.DocumentTypeDTO;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewFileDTO {

    @NotNull(message = "File kontent daxil edilməyib")
    private String content;
    @NotNull(message = "Content-Type daxil edilməyib")
    private String contentType;
    @NotNull(message = "OriginalFileName daxil edilməyib")
    private String originalFilename;
    @NotNull(message = "Sənədin tipi daxil edilməyib")
    private Integer documentTypeId;

}
