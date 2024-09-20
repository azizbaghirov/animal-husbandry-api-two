package az.eagro.animalhusbandry.api.service.model;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmAuditDTO {

    private String revisionType;
    private Instant revisonDate;
    private boolean activityStatus;


}
