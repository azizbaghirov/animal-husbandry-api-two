package az.eagro.animalhusbandry.repository.monitoring;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalMonitoringDecisionRepository extends JpaRepository<FinalMonitoringDecisionEntity, UUID> {

    @Query("SELECT fm FROM FinalMonitoringDecisionEntity fm "
            + " LEFT JOIN FETCH fm.files f"
            + " LEFT JOIN FETCH f.documentType"
            + " WHERE fm.certificationApplicationId = :certificationApplicationId ")
    Optional<FinalMonitoringDecisionEntity> findByCertificationApplicationId(Integer certificationApplicationId);

}
