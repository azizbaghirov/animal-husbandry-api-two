package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationSummaryDTO {

    private Integer id;
    private String applicationNumber;
    private String administrativeArea;
    private String farmSpecialization;
    private String animalType;
    private String animalSort;
    private Instant createdAt;
    private ApplicationStatus applicationStatus;
    private InitialMonitoringStatus initialMonitoringStatus;
    private FinalMonitoringStatus finalMonitoringStatus;
    private boolean permitDeletionApplication;

}
