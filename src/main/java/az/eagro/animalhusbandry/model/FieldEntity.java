package az.eagro.animalhusbandry.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Field")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldEntity {

    @Id
    @Column(name = "FieldId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "DataType", nullable = false, updatable = false)
    private DataType dataType;

    @Column(name = "Label", nullable = false, updatable = false, unique = true)
    private String label;

    @Column(name = "Name", nullable = false, updatable = false, unique = true)
    private String name;

    @Column(name = "Description", nullable = false, updatable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MeasurementUnitId", nullable = false)
    private MeasurementUnitEntity measurementUnit;

    @ManyToOne
    @JoinColumn(name = "FieldConfigurationId", insertable = false, updatable = false)
    private FieldConfigurationEntity fieldConfiguration;

    @Enumerated(EnumType.STRING)
    @Column(name = "FieldType", nullable = false, updatable = false)
    private FieldType fieldType;

    @Column(name = "Mandatory", nullable = false, insertable = false, updatable = false)
    private boolean mandatory;

    @Column(name = "FieldOrder")
    private Integer fieldOrder;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "FieldId")
    private List<FieldValidationRuleEntity> fieldValidationRules = new ArrayList();

}
