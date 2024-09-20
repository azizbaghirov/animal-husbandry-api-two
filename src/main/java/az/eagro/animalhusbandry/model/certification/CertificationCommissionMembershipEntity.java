package az.eagro.animalhusbandry.model.certification;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "CertificationCommissionMembership")
public class CertificationCommissionMembershipEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "CertificationCommissionMembershipId", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "MembershipYear", nullable = false)
    private Integer membershipYear;

}
