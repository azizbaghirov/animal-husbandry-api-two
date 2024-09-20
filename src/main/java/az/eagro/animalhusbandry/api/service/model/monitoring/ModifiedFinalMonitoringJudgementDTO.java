package az.eagro.animalhusbandry.api.service.model.monitoring;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifiedFinalMonitoringJudgementDTO {

    @NotNull(message = "Yekun monitorinq rəyinin identifikasiya nömrəsi vacib məlumatdır")
    private UUID id;

    @NotNull(message = "Bu vacib məlumatdır və \"Uyğundur\" və ya \"Uyğun deyil\" dəyərlərindən biri seçilə bilər.")
    private Boolean compliant;

    @NotNull(message = "Qeyd vacib məlumatdır")
    @Size(min = 3, max = 2048, message = "Bu vacib məlumatdır və 3-2048 simvol uzunluğunda ixtiyari mətn daxil edilə bilər.")
    private String justification;

}
