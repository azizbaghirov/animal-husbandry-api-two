package az.eagro.animalhusbandry.api.service.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewInitialMonitoringDecisionDTO {

    @NotNull(message = "Müraciətin id-si vacib məlumatdır")
    private Integer applicationId;

    @NotNull(message = "Uyğunluq statusu vacib məlumatdır")
    private Boolean compliant;

    @NotNull(message = "Qeyd vacib məlumatdır")
    @Size(min = 3, max = 1000, message = "'Qeyd' minimum 3, maksimum 1000 somvoldan ibarət ola bilər")
    private String justification;

    private List<NewFileDTO> files = new ArrayList<>();

}
