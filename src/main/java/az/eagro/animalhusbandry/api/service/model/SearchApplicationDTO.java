package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.api.PageRequest;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchApplicationDTO extends PageRequest {

    private Integer farmTypeId;
    private String pinTax;
    private Integer regionId;
    private Instant createdAt;
    private String applicationNumber;
    private ApplicationStatus applicationStatus;
    private InitialMonitoringStatus initialMonitoringStatus;
    private FinalMonitoringStatus finalMonitoringStatus;

}
