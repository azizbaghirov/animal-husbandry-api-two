package az.eagro.animalhusbandry.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PhysicalPerson")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalPersonEntity {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "PhysicalPersonId", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "PersonalIdentificationNumber", nullable = false, updatable = false, unique = true)
    private String personalIdentificationNumber;

    @Column(name = "TaxPayerIdentificationNumber", updatable = false, unique = true)
    private String taxPayerIdentificationNumber;

    @Column(name = "Name", nullable = false, updatable = false)
    private String name;

    @Column(name = "Surname", nullable = false, updatable = false)
    private String surname;

    @Column(name = "Patronymic", nullable = false, updatable = false)
    private String patronymic;

}