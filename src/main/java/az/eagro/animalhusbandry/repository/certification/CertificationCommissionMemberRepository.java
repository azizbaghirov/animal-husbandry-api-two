package az.eagro.animalhusbandry.repository.certification;

import az.eagro.animalhusbandry.model.UserRole;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMemberEntity;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMembershipEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationCommissionMemberRepository extends JpaRepository<CertificationCommissionMemberEntity, UUID> {

    @Query("SELECT c from CertificationCommissionMemberEntity c "
            + " LEFT JOIN FETCH c.operator o "
            + " JOIN FETCH o.grantedAuthorities g "
            + " WHERE c.membership = :membership AND g.role in :roles ")
    List<CertificationCommissionMemberEntity> findAllByMembership(CertificationCommissionMembershipEntity membership, List<UserRole> roles);

}
