package az.eagro.animalhusbandry.api.service.model;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialMonitoringDataDTO {

    private Integer id;
    private InitialMonitoringDecisionDTO monitoringDecision;
    private List<DiscoveredDataDTO> discoveredData;
    private List<VaccinationDTO> vaccinations;
    private boolean disinfected;

}
