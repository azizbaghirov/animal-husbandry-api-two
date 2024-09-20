package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MeasurementUnit")
@Getter
@Setter
public class MeasurementUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MeasurementUnitId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @Column(nullable = false, insertable = false, updatable = false, unique = true)
    private String label;

    @Column(nullable = false, insertable = false, updatable = false, unique = true)
    private String name;

    @Column(nullable = false, insertable = false, updatable = false)
    private String description;
}
