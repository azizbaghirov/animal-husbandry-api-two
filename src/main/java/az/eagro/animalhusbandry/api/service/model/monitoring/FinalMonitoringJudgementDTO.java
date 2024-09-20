package az.eagro.animalhusbandry.api.service.model.monitoring;

import az.eagro.animalhusbandry.api.service.model.OperatorSummaryDTO;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalMonitoringJudgementDTO {

    private UUID id;
    private Boolean compliant;
    private String justification;
    private OperatorSummaryDTO author;
    private Instant createdAt;
    private boolean authUserIsOwner;

}
