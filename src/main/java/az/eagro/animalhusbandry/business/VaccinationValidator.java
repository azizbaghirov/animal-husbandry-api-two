package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.InitialMonitoringDataEntity;
import az.eagro.animalhusbandry.model.VaccinationEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class VaccinationValidator {

    private final VaccinationManager vaccinationManager;

    public void ensureHasVaccinations(InitialMonitoringDataEntity initialMonitoringData) {

        List<VaccinationEntity> vaccinationsByFarmTypeId = vaccinationManager.getVaccinationsByFarmTypeId(
                initialMonitoringData.getMonitoringDecision().getApplication().getFarmType().getId());

        if (!vaccinationsByFarmTypeId.containsAll(initialMonitoringData.getVaccinations().stream().toList())) {
            throw new BusinessException("Vaksinasiya siyahısı təsərrüfat tipinə uyğun seçilməyib. ");
        }
    }
}
