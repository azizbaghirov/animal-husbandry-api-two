package az.eagro.animalhusbandry.api.service.model.certification;

import az.eagro.animalhusbandry.api.service.model.OperatorSummaryDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalMonitoringDecisionRegisteredDTO {

    private UUID id;
    private OperatorSummaryDTO createdBy;
    private Instant createdAt;
    private Boolean certified;
    private String justification;
    private List<FileDTO> files;
    private Integer certificationApplicationId;
    private boolean permitDecisionCancellation;

}
