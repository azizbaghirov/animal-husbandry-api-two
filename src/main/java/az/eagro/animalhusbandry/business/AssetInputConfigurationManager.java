package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.AssetInputConfigurationEntity;
import az.eagro.animalhusbandry.repository.AssetInputConfigurationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AssetInputConfigurationManager {

    private final AssetInputConfigurationRepository assetConfigurationRepository;

    public List<AssetInputConfigurationEntity> getNormsByFarmTypeIdAndFarmSpecializationId(Integer farmTypeId, Integer farmSpecializationId) {
        return assetConfigurationRepository.getByFarmTypeIdAndFarmSpecializationId(farmTypeId, farmSpecializationId);
    }
}
