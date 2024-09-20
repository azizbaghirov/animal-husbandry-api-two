package az.eagro.animalhusbandry.repository.certification;

import az.eagro.animalhusbandry.model.certification.CertificationCommissionMembershipEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationCommissionMembershipRepository extends JpaRepository<CertificationCommissionMembershipEntity, UUID> {

    @Query("SELECT e FROM CertificationCommissionMembershipEntity e WHERE e.membershipYear = :year")
    Optional<CertificationCommissionMembershipEntity> findMembershipByYear(@Param("year") int year);

}
