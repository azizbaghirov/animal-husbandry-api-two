package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.api.service.model.validation.ValidFarmSpecialization;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmActivityType;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ValidFarmSpecialization
public class NewFarmDTO {

    @NotNull(message = "İnzibati ərazi vahidini seçmək zəruridir")
    private Integer regionId;
    @NotNull(message = "Təsərrüfat tipi vacib məlumatdır")
    private Integer farmTypeId;
    @NotNull(message = "Fəaliyyət növünü seçmək zəruridir")
    private BreedingAnimalFarmActivityType activityType;
    @NotNull(message = "İnzibati ərazini seçmək zəruridir")
    private Integer administrativeAreaId;
    @NotNull(message = "Heyvan tipinin seçilməsi zəruridir")
    private Integer animalTypeId;
    @NotNull(message = "Heyvan cinsinin seçilməsi zəruridir")
    private String animalSortId;
    @Size(min = 3, max = 512, message = "Faktiki ünvan 3-512 simvol uzunluğunda təyin edilməlidir")
    @NotNull(message = "Faktiki ünvan zəruri məlumatdır")
    private String currentAddress;
    private Integer farmSpecializationId;
    @NotEmpty(message = "Ən azı bir torpaq sənədi seçmək zəruridir")
    private Set<Integer> actIds = new HashSet<>();
    @NotNull(message = "Sənədin tipi zəruri məlumatdır")
    private DocumentRequestDTO documentType;
    private Integer contourId;

}
