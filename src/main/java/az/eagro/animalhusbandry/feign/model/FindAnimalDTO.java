package az.eagro.animalhusbandry.feign.model;

import az.eagro.animalhusbandry.api.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAnimalDTO extends PageRequest {

    private String nicknameOrTagId;

}
