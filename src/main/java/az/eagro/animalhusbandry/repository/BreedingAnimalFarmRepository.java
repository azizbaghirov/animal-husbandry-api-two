package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.AnimalSortEntity;
import az.eagro.animalhusbandry.model.AnimalTypeEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedingAnimalFarmRepository extends JpaRepository<BreedingAnimalFarmEntity, Integer>,
        RevisionRepository<BreedingAnimalFarmEntity, Integer, Integer> {

    Page<BreedingAnimalFarmEntity> findAllByRegionIdAndFarmTypeIdAndFarmerAccountIdAndDeletedAtIsNullOrderByCreatedAtDesc(
            Integer regionId, Integer farmTypeId, UUID farmerId, Pageable pageRequest);

    boolean existsByFarmTypeIdAndFarmSpecializationIdAndAdministrativeAreaIdAndAnimalTypeAndAnimalSortAndFarmerAccountIdAndDeletedAtIsNull(
            Integer farmType, Integer farmSpecializationId, Integer areaId, AnimalTypeEntity animalType, AnimalSortEntity animalSort, UUID farmerId);

    boolean existsByFarmTypeIdAndAdministrativeAreaIdAndAnimalTypeAndAnimalSortAndFarmerAccountIdAndDeletedAtIsNull(
            Integer farmType, Integer areaId, AnimalTypeEntity animalType, AnimalSortEntity animalSort, UUID farmerId);

    boolean existsByIdAndFarmerAccountIdAndDeletedAtIsNull(Integer id, UUID farmerId);

    @Query("SELECT f FROM BreedingAnimalFarmEntity f JOIN FETCH f.fieldDocuments d WHERE f.id = :id AND f.farmerAccount.id = :farmerId "
            + " AND f.deletedAt IS NULL")
    Optional<BreedingAnimalFarmEntity> findByIdAndFarmerAccountId(Integer id, UUID farmerId);

    @Query("SELECT f FROM BreedingAnimalFarmEntity f JOIN FETCH f.farmSpecialization where f.id = :id ")
    BreedingAnimalFarmEntity getById(Integer id);

    boolean existsByAdministrativeAreaIdAndFarmSpecializationIdAndFarmerAccountIdAndDeletedAtIsNull(
            Integer areaId, Integer farmSpecializationId, UUID farmerId);

}
