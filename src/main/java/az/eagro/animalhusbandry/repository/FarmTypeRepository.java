package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmTypeRepository extends JpaRepository<BreedingAnimalFarmTypeEntity, Integer> {
}
