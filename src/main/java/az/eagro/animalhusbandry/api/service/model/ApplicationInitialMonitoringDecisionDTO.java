package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationInitialMonitoringDecisionDTO {

    private String applicationNumber;
    private FarmerAccountDTO farmer;
    private PhysicalPersonDTO createdBy;
    private BreedingAnimalFarmInitialMonitoringDecisionDTO farm;
    private InitialMonitoringStatus initialMonitoringStatus;

}
