package az.eagro.animalhusbandry.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnimalSummaryDTO {

    @JsonProperty("AnimalId")
    private Integer id;

    @JsonProperty("Nickname")
    private String nickname;

    @JsonProperty("Gender")
    private String gender;

    private String age;

    private String tagId;
}