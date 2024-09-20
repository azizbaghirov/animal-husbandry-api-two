package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.AnimalTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalTypeEntity, Integer> {

    List<AnimalTypeEntity> getAnimalTypeEntitiesByFarmTypeId(Integer farmTypeId);

}
