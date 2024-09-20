package az.eagro.animalhusbandry.api.service.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldValidationRuleDTO {

    private Integer id;
    private String message;
    private Integer minInteger;
    private Integer maxInteger;
    private BigDecimal minDecimal;
    private BigDecimal maxDecimal;
    private Integer scale;
    private Integer minLength;
    private Integer maxLength;
    private String expression;

}
