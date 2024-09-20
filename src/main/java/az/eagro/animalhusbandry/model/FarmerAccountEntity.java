package az.eagro.animalhusbandry.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "FarmerAccount")
public class FarmerAccountEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FarmerAccountId", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "FarmerAccountType", nullable = false)
    private FarmerAccountType farmerAccountType = FarmerAccountType.PHYSICAL_PERSON;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PhysicalPersonId", nullable = false, updatable = false)
    private PhysicalPersonEntity physicalPerson;

    @ManyToOne
    @JoinColumn(name = "LegalPersonId")
    private LegalPersonEntity legalPerson;

}