package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.LegalPersonEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalPersonRepository extends JpaRepository<LegalPersonEntity, UUID> {

    Optional<LegalPersonEntity> findByTaxPayerIdentificationNumber(String voen);

}
