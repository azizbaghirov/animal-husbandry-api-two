package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationDataEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationApplicationDataRepository extends JpaRepository<CertificationApplicationDataEntity, Integer> {

    @Query("SELECT cad FROM CertificationApplicationDataEntity cad "
            + " LEFT JOIN fetch cad.application c"
            + " LEFT JOIN c.farm f"
            + " WHERE c.deletedAt IS NULL AND ( :farmerId IS NULL OR c.farmer.id = :farmerId ) "
            + " AND ( COALESCE(:ids, null) IS NULL OR (c.farm.region.id in :ids) ) AND c.id = :id "
            + " AND ( :farmId IS NULL OR (f.id =:farmId  AND f.deletedAt IS NULL ))")
    Optional<CertificationApplicationDataEntity> findByApplicationIdOrFarmerIdOrRegionIds(
            Integer id, UUID farmerId, List<Integer> ids, Integer farmId);

    @Query("SELECT cad FROM CertificationApplicationDataEntity cad "
            + " LEFT JOIN cad.application c "
            + " WHERE c.deletedAt IS NULL AND c.applicationStatus = :applicationStatus "
            + " AND ( COALESCE(:ids, null) IS NULL OR (c.farm.region.id in :ids) ) AND c.id = :id ")
    Optional<CertificationApplicationDataEntity> findByApplicationIdAndApplicationStatusOrRegionIds(
            Integer id, ApplicationStatus applicationStatus, List<Integer> ids);

    Optional<CertificationApplicationDataEntity> findByApplicationId(Integer id);

}
