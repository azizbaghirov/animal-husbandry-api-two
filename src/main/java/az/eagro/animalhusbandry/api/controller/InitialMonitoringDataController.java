package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.InitialMonitoringDataService;
import az.eagro.animalhusbandry.api.service.model.InitialMonitoringDataDTO;
import az.eagro.animalhusbandry.api.service.model.NewInitialMonitoringDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/initial-monitoring-data")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Initial Monitoring Data Controller")
public class InitialMonitoringDataController {

    private final InitialMonitoringDataService initialMonitoringDataService;

    @Operation(summary = "Create InitialMonitoringData By Inspector")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('INSPECTOR')")
    public void createInitialMonitoringData(@Valid @RequestBody NewInitialMonitoringDataDTO newInitialMonitoringData) {
        initialMonitoringDataService.createInitialMonitoringData(newInitialMonitoringData);
    }

    @Operation(summary = "Get Initial monitoring data by applicatioId for FARMER")
    @GetMapping("/farmer-application/{id}")
    // todo: @PreAuthorize("hasAnyAuthority('FARMER')")
    public InitialMonitoringDataDTO getApplicationByIdForFarmer(@PathVariable Integer id) {
        return initialMonitoringDataService.getByApplicationIdForFarmer(id);
    }

    @Operation(summary = "Get Initial monitoring data by applicatioId for INSPECTOR")
    @GetMapping("/inspector-application/{id}")
    @PreAuthorize("hasAnyAuthority('INSPECTOR')")
    public InitialMonitoringDataDTO getApplicationByIdForInspector(@PathVariable Integer id) {
        return initialMonitoringDataService.getByApplicationIdForInspector(id);
    }

    @Operation(summary = "Get Initial monitoring data by applicationId for  SUPERVISOR, COMMISSION_CHAIRMAN and COMMISSION_MEMBER")
    @GetMapping("/commission-application/{id}")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER','SUPERVISOR')")
    public InitialMonitoringDataDTO getApplicationByIdForCommission(@PathVariable Integer id) {
        return initialMonitoringDataService.getByApplicationIdForCommission(id);
    }

    @Operation(summary = "Delete a initial monitoring decision")
    @DeleteMapping("/initial-monitoring-decision/{initialMonitoringDecisionId}")
    @PreAuthorize("hasAnyAuthority('INSPECTOR')")
    public void deleteById(@PathVariable("initialMonitoringDecisionId") Integer initialMonitoringDecisionId) {
        initialMonitoringDataService.deleteById(initialMonitoringDecisionId);
    }

}
