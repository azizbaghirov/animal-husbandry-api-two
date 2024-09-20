package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.AssetInputConfigurationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetInputConfigurationRepository extends JpaRepository<AssetInputConfigurationEntity, Integer> {

    List<AssetInputConfigurationEntity> getByFarmTypeIdAndFarmSpecializationId(Integer farmTypeId, Integer farmSpecializationId);

    List<AssetInputConfigurationEntity> getByFarmTypeId(Integer farmTypeId);


}
