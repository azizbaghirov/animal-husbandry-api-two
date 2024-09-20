package az.eagro.animalhusbandry.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "FieldValidationRule")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldValidationRuleEntity {

    @Id
    @Column(name = "FieldValidationRuleId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "Message")
    private String message;

    @Column(name = "MinInteger")
    private Integer minInteger;

    @Column(name = "MaxInteger")
    private Integer maxInteger;

    @Column(name = "MinDecimal")
    private BigDecimal minDecimal;

    @Column(name = "MaxDecimal")
    private BigDecimal maxDecimal;

    @Column(name = "Scale")
    private Integer scale;

    @Column(name = "MinLength")
    private Integer minLength;

    @Column(name = "MaxLength")
    private Integer maxLength;

    @Column(name = "Expression")
    private String expression;

}
