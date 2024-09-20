package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FinalMonitoringJudgementService;
import az.eagro.animalhusbandry.api.service.FinalMonitoringJudgementSummaryService;
import az.eagro.animalhusbandry.api.service.model.monitoring.FinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.FinalMonitoringJudgementSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.ModifiedFinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.NewFinalMonitoringJudgementDTO;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certification-application")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Final Monitoring Judgement API")
public class FinalMonitoringJudgementController {

    private final FinalMonitoringJudgementSummaryService finalMonitoringJudgementSummaryService;
    private final FinalMonitoringJudgementService finalMonitoringJudgementService;

    @Operation(summary = "Retrieve final monitoring judgements")
    @GetMapping("/{certificationApplicationId}/final-monitoring-judgement-summary")
    // todo: @PreAuthorize("hasAnyAuthority('FARMER','COMMISSION_CHAIRMAN','COMMISSION_MEMBER','INSPECTOR','SUPERVISOR')")
    public FinalMonitoringJudgementSummaryDTO getFinalMonitoringJudgementSummary(
            @PathVariable("certificationApplicationId") Integer certificationApplicationId) {
        return finalMonitoringJudgementSummaryService.getSummaryByCertificationApplicationId(certificationApplicationId);
    }

    @Operation(summary = "Retrieve detailed information about a final monitoring judgement")
    @GetMapping("/final-monitoring-judgement/{finalMonitoringJudgementId}")
    // todo: @PreAuthorize("hasAnyAuthority('FARMER','COMMISSION_CHAIRMAN','COMMISSION_MEMBER','INSPECTOR','SUPERVISOR')")
    public FinalMonitoringJudgementDTO getById(@PathVariable("finalMonitoringJudgementId") UUID finalMonitoringJudgementId) {
        return finalMonitoringJudgementService.getById(finalMonitoringJudgementId);
    }

    @Operation(summary = "Create a new final monitoring judgement")
    @PostMapping("/final-monitoring-judgement")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER')")
    public FinalMonitoringJudgementDTO create(@Valid @RequestBody NewFinalMonitoringJudgementDTO newFinalMonitoringJudgementDTO) {
        return finalMonitoringJudgementService.create(newFinalMonitoringJudgementDTO);
    }

    @Operation(summary = "Update a modified final monitoring judgement")
    @PutMapping("/final-monitoring-judgement")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER')")
    public FinalMonitoringJudgementDTO update(@Valid @RequestBody ModifiedFinalMonitoringJudgementDTO modifiedFinalMonitoringJudgementDTO) {
        return finalMonitoringJudgementService.update(modifiedFinalMonitoringJudgementDTO);
    }

    @Operation(summary = "Delete a final monitoring judgement")
    @DeleteMapping("/final-monitoring-judgement/{finalMonitoringJudgementId}")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER')")
    public void deleteById(@PathVariable("finalMonitoringJudgementId") UUID finalMonitoringJudgementId) {
        finalMonitoringJudgementService.deleteById(finalMonitoringJudgementId);
    }
}
