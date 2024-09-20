package az.eagro.animalhusbandry.repository.certification;

import az.eagro.animalhusbandry.model.certification.BreedingAnimalFarmCertificateEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedingAnimalFarmCertificateRepository extends JpaRepository<BreedingAnimalFarmCertificateEntity, UUID> {

    Optional<BreedingAnimalFarmCertificateEntity> findByCertificationApplicationId(Integer certificationApplicationId);

    @Query(value = "SELECT NEXT VALUE FOR AnimalHusbandry.SEQ_BreedingAnimalFarmCertification_RegistrationNumber", nativeQuery = true)
    Long getNextValRegisterNumberSequence();
}
