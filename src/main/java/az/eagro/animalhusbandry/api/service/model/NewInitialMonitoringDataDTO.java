package az.eagro.animalhusbandry.api.service.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewInitialMonitoringDataDTO {

    private NewInitialMonitoringDecisionDTO  monitoringDecision;
    private List<NewDiscoveredDataDTO> discoveredData = new ArrayList<>();
    private List<Integer> vaccinationIds = new ArrayList<>();
    private boolean disinfected;

}
