package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationValidation {

    private final BreedingAnimalFarmValidation farmValidation;
    private final CertificationApplicationManager certificationApplicationManager;
    private final FieldValidation fieldValidation;


    private void ensureHasPermissionForNewApplication(Integer farmId) {
        if (!certificationApplicationManager.hasPermissionForNewApplication(farmId)) {
            throw new BusinessException("Təsərrüfata aid imtina edilməmiş müraciət mövcuddur.");
        }
    }

    public void newApplicationValidation(CertificationApplicationEntity newApplication, List<FieldValueEntity> fieldValues) {
        farmValidation.ensureExistsAppropriateApplicationForFarm(newApplication);
        ensureHasPermissionForNewApplication(newApplication.getFarm().getId());
        fieldValidation.ensureAllFieldsSent(newApplication.getFarm().getId(), fieldValues);
    }

}
