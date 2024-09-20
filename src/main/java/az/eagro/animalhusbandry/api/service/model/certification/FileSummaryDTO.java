package az.eagro.animalhusbandry.api.service.model.certification;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

@Setter
@Getter
@Builder
public class FileSummaryDTO {

    private String fileName;
    private InputStream inputStream;
    private MediaType mediaType;

}
