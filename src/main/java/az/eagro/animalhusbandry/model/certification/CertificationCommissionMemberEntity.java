package az.eagro.animalhusbandry.model.certification;

import az.eagro.animalhusbandry.model.OperatorEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "CertificationCommissionMember")
public class CertificationCommissionMemberEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "CertificationCommissionMemberId", nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CertificationCommissionMembershipId", nullable = false, updatable = false)
    private CertificationCommissionMembershipEntity membership;

    @ManyToOne(optional = false)
    @JoinColumn(name = "OperatorId", nullable = false, updatable = false)
    private OperatorEntity operator;

}
