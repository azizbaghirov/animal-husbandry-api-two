package az.eagro.animalhusbandry.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LegalPerson")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalPersonEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "LegalPersonId", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "LegalPersonName", nullable = false,  updatable = false)
    private String name;

    @Column(name = "TaxPayerIdentificationNumber", nullable = false, updatable = false, unique = true)
    private String taxPayerIdentificationNumber;

    @Column(name = "HasStamp", nullable = false, updatable = false)
    private boolean hasStamp;

}
