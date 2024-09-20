package az.eagro.animalhusbandry.api.service.model.validation;

import az.eagro.animalhusbandry.api.service.FarmSpecializationService;
import az.eagro.animalhusbandry.api.service.model.NewFarmDTO;
import az.eagro.animalhusbandry.repository.BreedingAnimalFarmSpecializationRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmSpecializationValidator implements ConstraintValidator<ValidFarmSpecialization, NewFarmDTO> {

    private final FarmSpecializationService farmSpecializationService;
    private final BreedingAnimalFarmSpecializationRepository specializationRepository;

    @Override
    public void initialize(ValidFarmSpecialization constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewFarmDTO newFarmDTO, ConstraintValidatorContext context) {
        if (newFarmDTO.getFarmTypeId() != null) {

            Integer farmTypeId = newFarmDTO.getFarmTypeId();
            Integer farmSpecializationId = newFarmDTO.getFarmSpecializationId();

            if (farmSpecializationService.ensureExistsByFarmTypeId(farmTypeId) && (farmSpecializationId == null)) {
                validationMessage(context, "Təsərrüfat istiqamətini seçmək zəruridir");
                return false;
            }
            if (farmSpecializationId != null && specializationRepository.findByIdAndFarmTypeId(farmSpecializationId, farmTypeId).isEmpty()) {
                validationMessage(context, "Təsərrüfat istiqaməti seçilmiş təsərrüfat növünə aid deyil.");
                return false;
            }
        }
        return true;
    }

    private static void validationMessage(ConstraintValidatorContext context, String messageTemplate) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addPropertyNode("farmSpecializationId")
                .addConstraintViolation();
    }

}