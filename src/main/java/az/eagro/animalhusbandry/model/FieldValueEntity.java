package az.eagro.animalhusbandry.model;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "FieldValue")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FieldValueId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FieldId", updatable = false)
    private FieldEntity field;

    @Column(name = "IntValue")
    private Integer intValue;

    @Column(name = "DecimalValue")
    private BigDecimal decimalValue;

    @Column(name = "BooleanValue")
    private Boolean booleanValue;

    @Column(name = "TextValue")
    private String textValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApplicationDataId")
    private CertificationApplicationDataEntity applicationData;

}
