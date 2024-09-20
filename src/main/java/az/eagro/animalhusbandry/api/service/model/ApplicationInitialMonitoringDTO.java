package az.eagro.animalhusbandry.api.service.model;


import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationInitialMonitoringDTO {

    private Integer id;
    private String applicationNumber;
    private ApplicationStatus applicationStatus;
    private InitialMonitoringStatus initialMonitoringStatus;

}
