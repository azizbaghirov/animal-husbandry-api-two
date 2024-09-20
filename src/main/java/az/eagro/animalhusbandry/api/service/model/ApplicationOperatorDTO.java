package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationOperatorDTO {

    private Integer id;
    private String applicationNumber;
    private FarmSummaryDTO farm;
    private FarmerAccountDTO farmer;
    private Instant createdAt;
    private ApplicationStatus applicationStatus;
    private InitialMonitoringStatus initialMonitoringStatus;
    private FinalMonitoringStatus finalMonitoringStatus;

}
