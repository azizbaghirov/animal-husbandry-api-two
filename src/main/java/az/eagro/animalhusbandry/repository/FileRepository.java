package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.FileEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
}
