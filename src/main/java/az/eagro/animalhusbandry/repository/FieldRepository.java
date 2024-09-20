package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.FieldEntity;
import az.eagro.animalhusbandry.model.FieldType;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<FieldEntity, Integer> {

    @Query("SELECT a FROM FieldEntity a "
            + " LEFT JOIN a.fieldConfiguration fc "
            + " WHERE ( :farmSpecializationId IS NULL OR fc.farmSpecialization.id = :farmSpecializationId) "
            + " AND fc.farmType.id = :farmTypeId "
            + " AND a.fieldType = :fieldType ")
    List<FieldEntity> findAllAssetsByFarmTypeIdAndFarmSpecializationId(FieldType fieldType, Integer farmTypeId, Integer farmSpecializationId);

    @Query("SELECT a FROM FieldEntity a "
            + " LEFT JOIN a.fieldConfiguration fc "
            + " WHERE fc.id = :fieldConfigurationId ORDER BY a.fieldOrder ASC")
    @EntityGraph(attributePaths = {"fieldValidationRules", "measurementUnit"})
    List<FieldEntity> findAllByFieldConfigurationId(Integer fieldConfigurationId);
}
