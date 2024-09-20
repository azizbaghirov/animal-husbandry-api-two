package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerAccountRepository extends JpaRepository<FarmerAccountEntity, UUID> {

    boolean existsByPhysicalPersonIdAndLegalPersonTaxPayerIdentificationNumber(UUID physicalPersonId, String taxNumber);

    boolean existsByPhysicalPersonIdAndLegalPersonIsNull(UUID physicalPersonId);

    Optional<FarmerAccountEntity> findByPhysicalPersonIdAndLegalPersonTaxPayerIdentificationNumber(UUID physicalPersonId, String taxNumber);

    Optional<FarmerAccountEntity> findByPhysicalPersonIdAndLegalPersonIsNull(UUID physicalPersonId);
}
