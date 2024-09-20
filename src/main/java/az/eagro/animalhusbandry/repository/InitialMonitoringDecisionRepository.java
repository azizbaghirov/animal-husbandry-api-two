package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InitialMonitoringDecisionRepository extends JpaRepository<InitialMonitoringDecisionEntity, Integer> {

    Optional<InitialMonitoringDecisionEntity> findByApplicationId(Integer id);

    @Query("SELECT imd FROM InitialMonitoringDecisionEntity imd "
            + " LEFT JOIN imd.application a "
            + " LEFT JOIN FETCH imd.files f "
            + " WHERE a.deletedAt IS NULL AND ( :farmer IS NULL OR a.farmer = :farmer ) "
            + " AND ( COALESCE(:ids, null) IS NULL OR (a.farm.region.id in :ids) ) AND a.id = :applicationId ")
    Optional<InitialMonitoringDecisionEntity> findByApplicationIdOrFarmerIdOrRegionIds(
            Integer applicationId, FarmerAccountEntity farmer, List<Integer> ids);

    boolean existsByApplicationId(Integer applicationId);

}
