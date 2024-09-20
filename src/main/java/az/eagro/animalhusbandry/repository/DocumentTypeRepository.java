package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.DocumentTypeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, Integer> {

    Optional<List<DocumentTypeEntity>> findAllByLabelIn(List<String> labels);

}
