package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FinalMonitoringCertificationApplicationStateService;
import az.eagro.animalhusbandry.api.service.FinalMonitoringDecisionCancellationService;
import az.eagro.animalhusbandry.api.service.FinalMonitoringDecisionRegistrationService;
import az.eagro.animalhusbandry.api.service.FinalMonitoringDecisionService;
import az.eagro.animalhusbandry.api.service.model.FinalMonitoringCertificationApplicationStateDTO;
import az.eagro.animalhusbandry.api.service.model.FinalMonitoringDecisionStateDTO;
import az.eagro.animalhusbandry.api.service.model.NewDeniedFinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.NewPermittedFinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FinalMonitoringDecisionRegisteredDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certification-application/{certificationApplicationId}")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Final Monitoring Decision API")
public class FinalMonitoringDecisionController {

    private final FinalMonitoringDecisionService finalMonitoringDecisionService;
    private final FinalMonitoringDecisionRegistrationService finalMonitoringDecisionRegistrationService;
    private final FinalMonitoringDecisionCancellationService finalMonitoringDecisionCancellationService;
    private final FinalMonitoringCertificationApplicationStateService stateService;

    @Operation(summary = "Retrieve state of certification decision")
    @GetMapping("/final-monitoring-decision-state")
    public FinalMonitoringDecisionStateDTO getFinalMonitoringDecisionState(
            @PathVariable("certificationApplicationId") Integer certificationApplicationId) {
        return finalMonitoringDecisionService.getFinalMonitoringDecisionState(certificationApplicationId);
    }

    @Operation(summary = "Retrieve state of final monitoring and certification application")
    @GetMapping("/final-monitoring-decision-and-certification-application-state")
    public FinalMonitoringCertificationApplicationStateDTO getFinalMonitoringAndCertificationApplicationState(
            @PathVariable("certificationApplicationId") Integer certificationApplicationId) {
        return stateService.getFinalMonitoringCertificationApplicationState(certificationApplicationId);
    }

    @Operation(summary = "Retrieve information about a final monitoring decision")
    @GetMapping("/final-monitoring-decision")
    // todo: @PreAuthorize("hasAnyAuthority('FARMER','COMMISSION_CHAIRMAN','COMMISSION_MEMBER','INSPECTOR','SUPERVISOR')")
    public Optional<FinalMonitoringDecisionRegisteredDTO> getFinalMonitoringDecision(
            @PathVariable("certificationApplicationId") Integer certificationApplicationId) {
        return finalMonitoringDecisionService.findByCertificationApplicationId(certificationApplicationId);
    }

    @Operation(summary = "Register a final monitoring decision")
    @PutMapping("/final-monitoring-decision-permitted")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN')")
    public FinalMonitoringDecisionDTO createPermitDecision(
            @Valid @RequestBody NewPermittedFinalMonitoringDecisionDTO newPermittedFinalMonitoringDecisionDTO) {
        return finalMonitoringDecisionRegistrationService.createPermittedDecision(newPermittedFinalMonitoringDecisionDTO);
    }

    @Operation(summary = "Register a final monitoring decision")
    @PutMapping("/final-monitoring-decision-denied")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN')")
    public FinalMonitoringDecisionDTO createDeniedDecision(@Valid @RequestBody NewDeniedFinalMonitoringDecisionDTO newDeniedDecisionDTO) {
        return finalMonitoringDecisionRegistrationService.createDeniedDecision(newDeniedDecisionDTO);
    }

    @Operation(summary = "Delete a final monitoring decision")
    @DeleteMapping("/final-monitoring-decision/{finalMonitoringDecisionId}")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN')")
    public void deletedById(@PathVariable("finalMonitoringDecisionId") UUID finalMonitoringDecisionId) {
        finalMonitoringDecisionCancellationService.deletedById(finalMonitoringDecisionId);
    }
}
