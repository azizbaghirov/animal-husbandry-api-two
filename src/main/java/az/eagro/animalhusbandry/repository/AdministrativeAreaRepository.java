package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.AdministrativeAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeAreaRepository extends JpaRepository<AdministrativeAreaEntity, Integer> {
}
