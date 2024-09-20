package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "AnimalSort")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalSortEntity {

    @Id
    @Column(name = "AnimalSortId", nullable = false, insertable = false, updatable = false, unique = true)
    private String id;

    @Column(name = "Name", nullable = false, insertable = false, updatable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AnimalTypeId", nullable = false, insertable = false, updatable = false)
    private AnimalTypeEntity animalType;

    @Column(name = "Active", nullable = false, insertable = false, updatable = false)
    private boolean active;

}
