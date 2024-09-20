package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedingAnimalFarmSpecializationRepository extends JpaRepository<BreedingAnimalFarmSpecializationEntity, Integer> {

    @Query("SELECT spec FROM FieldConfigurationEntity fc "
            + " JOIN fc.farmSpecialization spec  "
            + " WHERE spec.farmType.id = :farmTypeId AND fc.animalType.id = :animalTypeId")
    List<BreedingAnimalFarmSpecializationEntity> findByFarmTypeIdAndAnimalTypeId(Integer farmTypeId, Integer animalTypeId);

    Optional<BreedingAnimalFarmSpecializationEntity> findByIdAndFarmTypeId(Integer specId, Integer farmTypeId);

    boolean existsByFarmTypeId(Integer farmTypeId);
}
