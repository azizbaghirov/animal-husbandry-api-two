package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.AssetInputConfigurationDTO;
import az.eagro.animalhusbandry.business.AssetInputConfigurationManager;
import az.eagro.animalhusbandry.model.AssetInputConfigurationEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AssetConfigurationService {

    private final Mapper<AssetInputConfigurationEntity, AssetInputConfigurationDTO> assetInputConfigurationEntityToDtoMapper;
    private final AssetInputConfigurationManager normService;

    public List<AssetInputConfigurationDTO> getAssetsByFarmTypeIdAndFarmSpecializationId(Integer farmTypeId, Integer farmSpecializationId) {
        return assetInputConfigurationEntityToDtoMapper
                .map(normService.getNormsByFarmTypeIdAndFarmSpecializationId(farmTypeId, farmSpecializationId));
    }

}
