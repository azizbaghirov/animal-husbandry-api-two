package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.CertificationApplicationDataService;
import az.eagro.animalhusbandry.api.service.model.CertificationApplicationDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/certification-application-data")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Certification Application Data Controller")
public class CertificationApplicationDataController {

    private final CertificationApplicationDataService applicationDataService;

    @Operation(summary = "Certification Application and Field Values for starting initial monitoring (for INSPECTOR)")
    @GetMapping("/initial-monitoring/inspector/{id}")
    @PreAuthorize("hasAnyAuthority('INSPECTOR')")
    public CertificationApplicationDataDTO getApplicationByIdForInitialMonitoring(@PathVariable Integer id) {
        return applicationDataService.getByApplicationIdAndIsRegistered(id);
    }

}
