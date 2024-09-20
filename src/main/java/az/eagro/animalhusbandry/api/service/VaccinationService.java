package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.VaccinationDTO;
import az.eagro.animalhusbandry.business.VaccinationManager;
import az.eagro.animalhusbandry.model.VaccinationEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class VaccinationService {

    private final VaccinationManager vaccinationManager;
    private final Mapper<VaccinationEntity, VaccinationDTO> vaccinationModelDTOMapper;

    public List<VaccinationDTO> getVaccinationsByFarmTypeId(Integer farmTypeId) {
        return vaccinationModelDTOMapper.map(vaccinationManager.getVaccinationsByFarmTypeId(farmTypeId));
    }

    public List<VaccinationDTO> getByInitMonitoringDataId(Integer id) {
        List<VaccinationEntity> vaccination = vaccinationManager.getVaccinationsByInitMonitoringDataId(id);
        return vaccinationModelDTOMapper.map(vaccination);
    }

}
