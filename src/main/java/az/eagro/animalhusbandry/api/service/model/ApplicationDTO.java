package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationDTO {

    private FarmerAccountDTO farmer;
    private String applicationNumber;
    private Instant createdAt;
    private ApplicationStatus applicationStatus;
    private String veterinaryContract;
    private String note;
    private PhysicalPersonDTO createdBy;
    private BreedingAnimalFarmSummaryDTO farm;
    private AnimalTypeDTO animalType;
    private AnimalSortDTO animalSort;
    private Set<FieldValueDTO> fieldValues = new HashSet<>();
    private InitialMonitoringStatus initialMonitoringStatus;
    private FinalMonitoringStatus finalMonitoringStatus;

}
