package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.api.service.model.certification.FileDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitialMonitoringDecisionDTO {

    private Integer id;
    private ApplicationInitialMonitoringDecisionDTO application;
    private Boolean compliant;
    private String justification;
    private List<UUID> fileIds;
    private OperatorSummaryDTO createdBy;
    private Instant createdAt;

}
