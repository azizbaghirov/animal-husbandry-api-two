package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.FieldDocumentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDocumentRepository extends JpaRepository<FieldDocumentEntity, Integer> {

    @Query("select a from BreedingAnimalFarmEntity f join f.fieldDocuments a where f.id = :farmId and f.farmerAccount = :farmer ")
    List<FieldDocumentEntity> getByFarmId(Integer farmId, FarmerAccountEntity farmer);

}
