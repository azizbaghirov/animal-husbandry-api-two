package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.VaccinationEntity;
import az.eagro.animalhusbandry.repository.VaccinationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class VaccinationManager {

    private final VaccinationRepository vaccinationRepository;

    public List<VaccinationEntity> getVaccinationsByFarmTypeId(Integer farmTypeId) {
        return vaccinationRepository.findByFarmTypeId(farmTypeId);
    }

    public List<VaccinationEntity> getVaccinationsByInitMonitoringDataId(Integer id) {
        return vaccinationRepository.getByInitialMonitoringDataId(id);
    }

}
