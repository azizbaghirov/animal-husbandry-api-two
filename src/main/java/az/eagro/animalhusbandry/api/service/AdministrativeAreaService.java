package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.AdministrativeAreaDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.shared.ektis.EktisClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AdministrativeAreaService {

    private final EktisClient ektisClient;


    public List<AdministrativeAreaDTO> getAdministrativeAreas(Integer regionId) {
        var administrativeAreas = ektisClient.getAdministrativeAreas(regionId);
        if (administrativeAreas.isEmpty()) {
            throw new BusinessException("İnzibati ərazi siyahısı boşdur.");
        }
        return administrativeAreas;
    }


    private AdministrativeAreaDTO getAdministrativeAreaFromEktis(Integer areaId) {
        var administrativeAreas = ektisClient.getAdministrativeAreas(areaId);
        AdministrativeAreaDTO administrativeArea;
        if (administrativeAreas != null) {
            administrativeArea = administrativeAreas.stream().filter(areaDTO -> areaDTO.getId().equals(areaId)).findFirst().orElseThrow(
                    () -> new BusinessException("İnzibati ərazi mövcud deyil."));
        } else {
            throw new BusinessException("İnzibati ərazi siyahısı boşdur.");
        }
        return administrativeArea;
    }

}
