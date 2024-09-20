package az.eagro.animalhusbandry.api.service;


import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.RegionManager;
import az.eagro.animalhusbandry.model.RegionEntity;
import az.eagro.animalhusbandry.shared.ektis.EktisClient;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RegionService {

    private final EktisClient ektisClient;
    private final RegionManager regionManager;
    private final Mapper<RegionDTO, RegionEntity> newRegionModelMapper;
    private final Mapper<RegionEntity, RegionDTO> regionModelDTOMapper;

    public RegionDTO getRegion(Integer regionId) {
        RegionDTO regionDTO;
        var region = regionManager.getById(regionId);
        if (region != null) {
            return regionModelDTOMapper.map(region);
        } else {
            regionDTO = getRegionFromEktis(regionId);
            regionManager.saveRegion(newRegionModelMapper.map(regionDTO));
        }
        return regionDTO;
    }

    private RegionDTO getRegionFromEktis(Integer regionId) {
        var regions = ektisClient.getRegions();
        RegionDTO region;
        if (regions != null) {
            region = regions.stream().filter(regionDTO -> regionDTO.getId().equals(regionId)).findFirst().orElseThrow(
                    () -> new BusinessException("Region mövcud deyil."));
        } else {
            throw new BusinessException("Region siyahısı boşdur.");
        }
        return region;
    }

    public List<RegionDTO> getRegions() {
        return regionModelDTOMapper.map(regionManager.getRegions());
    }

}
