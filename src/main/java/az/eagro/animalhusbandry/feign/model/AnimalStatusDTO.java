package az.eagro.animalhusbandry.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnimalStatusDTO {

    @JsonProperty("AnimalId")
    private Integer animalId;

    @JsonProperty("Nickname")
    private String nickname;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("age")
    private String age;

    private String tagId;

    private String currentAnimalHistoryTypeName;
}
