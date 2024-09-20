package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPersonEntity, UUID> {

}
