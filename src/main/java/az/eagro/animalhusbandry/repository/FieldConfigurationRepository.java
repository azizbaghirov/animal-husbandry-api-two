package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.FieldConfigurationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldConfigurationRepository extends JpaRepository<FieldConfigurationEntity, Integer> {


    @Query("SELECT fc FROM FieldConfigurationEntity fc "
            + " WHERE ( :farmSpecializationId IS NULL OR fc.farmSpecialization.id = :farmSpecializationId) "
            + " AND fc.farmType.id = :farmTypeId AND fc.animalType.id = :animalTypeId")
    Optional<FieldConfigurationEntity> findByFarmTypeAndFarmSpecializationAndAnimal(
            Integer farmTypeId, Integer farmSpecializationId, Integer animalTypeId);
}
