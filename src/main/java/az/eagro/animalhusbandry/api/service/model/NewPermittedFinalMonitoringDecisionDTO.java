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
public class NewPermittedFinalMonitoringDecisionDTO {

    @NotNull(message = "Müraciətin identifikasiya nömrəsi vacib məlumatdır")
    private Integer certificationApplicationId;

    @DateLimitation(message = "Uçota alınma tarixi yalnız cari tarixdən 30 gün öncəki tarixədək olan təqvim günlərindən biri təyin edilə bilər.")
    @NotNull(message = "Uçota alınma tarixi vacib məlumatdır")
    private Instant registeredAt;

    @NotNull(message = "Qeyd vacib məlumatdır")
    @Size(min = 3, max = 2048, message = "Bu vacib məlumatdır və 3-2048 simvol uzunluğunda ixtiyari mətn daxil edilə bilər.")
    private String justification;

    @NotNull(message = "Şəhadətnamə vacib məlumatdır.")
    private NewCertificateFileDTO certificateFile;

    private List<NewFileDTO> files = new ArrayList<>();

}
