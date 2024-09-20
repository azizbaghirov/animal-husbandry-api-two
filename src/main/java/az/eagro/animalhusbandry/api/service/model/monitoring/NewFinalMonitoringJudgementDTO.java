package az.eagro.animalhusbandry.api.service.model.monitoring;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFinalMonitoringJudgementDTO {


    @NotNull(message = "Müraciətin identifikasiya nömrəsi vacib məlumatdır")
    private Integer certificationApplicationId;

    @NotNull(message = "Bu vacib məlumatdır və \"Uyğundur\" və ya \"Uyğun deyil\" dəyərlərindən biri seçilə bilər.")
    private Boolean compliant;

    @NotNull(message = "Qeyd vacib məlumatdır")
    @Size(min = 3, max = 2048, message = "Bu vacib məlumatdır və 3-2048 simvol uzunluğunda ixtiyari mətn daxil edilə bilər.")
    private String justification;

}
