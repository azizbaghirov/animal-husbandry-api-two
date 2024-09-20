package az.eagro.animalhusbandry.repository.monitoring;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalMonitoringJudgementRepository extends JpaRepository<FinalMonitoringJudgementEntity, UUID> {

    boolean existsByCertificationApplicationIdAndCreatedById(Integer id, UUID operatorId);

    boolean existsByCertificationApplicationId(Integer applicationId);

    Optional<FinalMonitoringJudgementEntity> findByIdAndCreatedById(UUID judgementId, UUID operatorId);

    @Query("SELECT c FROM FinalMonitoringJudgementEntity c "
            + " LEFT JOIN FETCH c.createdBy o "
            + " JOIN FETCH o.grantedAuthorities "
            + " WHERE c.certificationApplicationId = :applicationId ")
    List<FinalMonitoringJudgementEntity> findAllByCertificationApplicationId(Integer applicationId);
}
