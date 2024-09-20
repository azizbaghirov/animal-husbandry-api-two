package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.AnimalSortEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalSortRepository extends JpaRepository<AnimalSortEntity, String> {

    List<AnimalSortEntity> getAnimalSortEntitiesByAnimalTypeIdAndActiveIsTrue(Integer animalTypeId);

    boolean existsByIdAndAnimalTypeIdAndAnimalTypeFarmTypeId(String animalSortId, Integer animalTypeId, Integer farmTypeId);

}
