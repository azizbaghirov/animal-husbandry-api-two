package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DocumentType")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentTypeId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "Name", nullable = false, insertable = false, updatable = false, unique = true)
    private String name;

    @Column(name = "Label", nullable = false, insertable = false, updatable = false, unique = true)
    private String label;

}
