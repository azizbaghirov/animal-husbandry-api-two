package az.eagro.animalhusbandry.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FieldDocumentDTO {

    @JsonProperty("id")
    @NotNull(message = "Id vacib məlumatdır")
    private Integer fieldId;

    @JsonProperty("uuid")
    @NotNull(message = "Sahə uuid-si vacib məlumatdır")
    private String fieldUuid;

    @NotNull(message = "Sahə vacib məlumatdır")
    private BigDecimal spaceHa;

    @NotNull(message = "Ad vacib məlumatdır")
    private String name;

    private DocumentDTO document;

    private List<ContourDTO> contours = new ArrayList<>();



}
