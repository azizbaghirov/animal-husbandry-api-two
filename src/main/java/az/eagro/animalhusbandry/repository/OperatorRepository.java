package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.OperatorEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorEntity, UUID> {

    @EntityGraph(attributePaths = {"grantedAuthorities", "regions"})
    Optional<OperatorEntity> findByPersonalIdentificationNumber(String pin);
}
