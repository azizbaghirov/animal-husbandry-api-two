package az.eagro.animalhusbandry.repository;


import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationApplicationRepository extends JpaRepository<CertificationApplicationEntity, Integer> {

    @Query("SELECT e FROM CertificationApplicationEntity e "
            + " WHERE e.deletedAt IS NULL AND e.id = :certificationApplicationId and e.farm.region.id in :regionIds")
    Optional<CertificationApplicationEntity> findByIdAndRegionIds(Integer certificationApplicationId, List<Integer> regionIds);

    @Query("SELECT e FROM CertificationApplicationEntity e "
            + " WHERE e.deletedAt IS NULL AND e.id = :certificationApplicationId AND e.farmer = :farmerAccount")
    Optional<CertificationApplicationEntity> findByIdAndFarmerAccount(Integer certificationApplicationId, FarmerAccountEntity farmerAccount);

    @Query("SELECT a FROM CertificationApplicationEntity a "
            + " LEFT JOIN a.farm f "
            + " LEFT JOIN f.farmSpecialization fs "
            + " WHERE a.deletedAt IS NULL AND (:animalSortId IS NULL OR f.animalSort.id = :animalSortId) "
            + " AND (:animalTypeId IS NULL OR f.animalType.id = :animalTypeId) "
            + " AND (:applicationStatus IS NULL OR a.applicationStatus = :applicationStatus) "
            + " AND (:administrativeAreaId IS NULL OR f.administrativeArea.id = :administrativeAreaId) "
            + " AND (:farmSpecId IS NULL OR fs.id = :farmSpecId) "
            + " AND a.farm.farmType.id = :farmTypeId AND a.farmer.id = :farmerId "
            + " AND f.id =:farmId AND f.deletedAt IS NULL ORDER BY a.createdAt DESC ")
    Page<CertificationApplicationEntity> findByFarmerAll(
            Integer farmTypeId, Integer farmId, UUID farmerId, Integer farmSpecId, Integer administrativeAreaId,
            String animalSortId, Integer animalTypeId, ApplicationStatus applicationStatus, Pageable pageable);

    @Query(value = "SELECT NEXT VALUE FOR AnimalHusbandry.SEQ_CertificationApplication_ApplicationNumber", nativeQuery = true)
    Long getNextValApplicationNumberSequence();

    @Query("SELECT a FROM CertificationApplicationEntity a "
            + " JOIN a.farmer f"
            + " LEFT JOIN f.legalPerson l "
            + " LEFT JOIN f.physicalPerson p "
            + " WHERE a.deletedAt IS NULL AND (:applicationStatus IS NULL OR a.applicationStatus = :applicationStatus) "
            + " AND (:initialStatus IS NULL OR a.initialMonitoringStatus = :initialStatus) "
            + " AND (:finalStatus IS NULL OR a.finalMonitoringStatus = :finalStatus) "
            + " AND (:regionId IS NULL OR a.farm.region.id = :regionId) "
            + " AND (:taxPayerNum IS NULL OR l.taxPayerIdentificationNumber = :taxPayerNum ) "
            + " AND (:pin IS NULL OR (p.personalIdentificationNumber = :pin AND l IS NULL)) "
            + " AND (:startOfDay IS NULL OR ((a.createdAt >= :startOfDay) AND (a.createdAt <= :endOfDay)) ) "
            + " AND (:applicationNumber IS NULL OR a.applicationNumber = :applicationNumber) "
            + " AND (:farmTypeId IS NULL OR a.farm.farmType.id = :farmTypeId) "
            + " AND ( COALESCE(:ids, null) IS NULL OR (a.farm.region.id in :ids) ) ORDER BY a.createdAt DESC ")
    Page<CertificationApplicationEntity> findAll(
            Integer farmTypeId, List<Integer> ids, String pin, String taxPayerNum, Integer regionId,
            Instant startOfDay, Instant endOfDay, String applicationNumber, ApplicationStatus applicationStatus,
            InitialMonitoringStatus initialStatus, FinalMonitoringStatus finalStatus, Pageable pageable);

    @Query("SELECT c FROM CertificationApplicationEntity c LEFT JOIN c.farm f WHERE c.deletedAt IS NULL AND f.id = :farmId")
    List<CertificationApplicationEntity> findByFarmId(Integer farmId);

    @Query("SELECT c FROM CertificationApplicationEntity c WHERE c.deletedAt IS NULL AND c.id = :id")
    Optional<CertificationApplicationEntity> findById(Integer id);

    @Modifying
    @Query("UPDATE CertificationApplicationEntity c SET c.deletedAt = current_timestamp WHERE c.id = :id")
    void deleteCertificationApplication(Integer id);

    @Query(" SELECT COUNT(DISTINCT ca) FROM CertificationApplicationEntity ca "
            + " LEFT JOIN InitialMonitoringDecisionEntity im ON im.application = ca "
            + " LEFT JOIN FinalMonitoringDecisionEntity fm ON fm.certificationApplicationId = ca.id "
            + " WHERE fm.certificationApplicationId IS NULL AND im.application IS NOT NULL AND ca.createdAt < :thirtyDaysAgo ")
    Integer countDelayedApplication(Instant thirtyDaysAgo);

    @Query("SELECT c FROM CertificationApplicationEntity c WHERE c.deletedAt IS NULL AND c.id = :applicationId ")
    @EntityGraph(attributePaths = {"animalIds"})
    CertificationApplicationEntity getAnimalIdsByApplicationId(Integer applicationId);

}
