package az.eagro.animalhusbandry.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministrativeAreaDTO {

    private Integer id;
    private String name;
    private String code;

}
