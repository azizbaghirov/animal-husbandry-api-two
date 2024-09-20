package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.InitialMonitoringDataEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InitialMonitoringDataRepository extends JpaRepository<InitialMonitoringDataEntity, Integer> {
    @Query("SELECT imd FROM InitialMonitoringDataEntity imd "
            + " LEFT JOIN FETCH imd.discoveredData dd "
            + " WHERE  imd.monitoringDecision.id = :decisionId ")
    Optional<InitialMonitoringDataEntity> findByDecisionId(Integer decisionId);
}
