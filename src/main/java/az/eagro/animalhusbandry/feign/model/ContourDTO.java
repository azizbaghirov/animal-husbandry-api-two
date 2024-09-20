
package az.eagro.animalhusbandry.feign.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ContourDTO {

    private Integer id;
    private BigDecimal spaceHa;
    private String name;

}
