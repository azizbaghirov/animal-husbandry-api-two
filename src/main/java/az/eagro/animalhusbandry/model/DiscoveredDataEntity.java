package az.eagro.animalhusbandry.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "DiscoveredData")
@Entity
public class DiscoveredDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscoveredDataId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DiscoveredFieldValueId")
    private FieldValueEntity discoveredValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DeclaredFieldValueId", nullable = false)
    private FieldValueEntity declaredValue;

    @Column(name = "ValuesEqual", nullable = false)
    private boolean valuesEqual;

}
