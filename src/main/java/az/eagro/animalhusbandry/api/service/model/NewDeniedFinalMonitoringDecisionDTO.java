package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.api.service.model.validation.DateLimitation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDeniedFinalMonitoringDecisionDTO {

    @NotNull(message = "Müraciətin identifikasiya nömrəsi vacib məlumatdır")
    private Integer certificationApplicationId;

    @NotNull(message = "Qeyd vacib məlumatdır")
    @Size(min = 3, max = 2048, message = "Bu vacib məlumatdır və 3-2048 simvol uzunluğunda ixtiyari mətn daxil edilə bilər.")
    private String justification;

}
