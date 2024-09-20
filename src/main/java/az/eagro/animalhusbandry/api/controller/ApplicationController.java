package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.PageResponse;
import az.eagro.animalhusbandry.api.service.BreedingAnimalFarmCertificateService;
import az.eagro.animalhusbandry.api.service.CertificationApplicationDataService;
import az.eagro.animalhusbandry.api.service.CertificationApplicationService;
import az.eagro.animalhusbandry.api.service.model.ApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationOperatorDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmCertificatePreviewDTO;
import az.eagro.animalhusbandry.api.service.model.FindApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.NewApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.SearchApplicationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/application")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Breeding Animal Application Controller")
public class ApplicationController {

    private final CertificationApplicationService certificationApplicationService;
    private final CertificationApplicationDataService applicationDataService;
    private final BreedingAnimalFarmCertificateService breedingAnimalFarmCertificateService;

    @Operation(summary = " Search Applications by FarmTypeId For 'FARMER' Role")
    @GetMapping(value = "/{farmTypeId}/{farmId}/search")
    // todo: @PreAuthorize("hasAnyAuthority('FARMER')")
    public PageResponse<ApplicationSummaryDTO> searchApplication(
            @PathVariable("farmTypeId") Integer farmTypeId,  @PathVariable("farmId") Integer farmId, FindApplicationDTO findApplicationDTO) {
        return certificationApplicationService.findAllForFarmer(farmTypeId, farmId, findApplicationDTO);
    }

    @Operation(summary = " Search Applications For 'INSPECTOR' Role")
    @GetMapping(value = "/inspector/search")
    @PreAuthorize("hasAnyAuthority('INSPECTOR')")
    public PageResponse<ApplicationOperatorDTO> searchApplicationForInspector(SearchApplicationDTO search) {
        return certificationApplicationService.findAllForInspector(search);
    }

    @Operation(summary = " Search Applications For 'COMMISSION_CHAIRMAN', 'COMMISSION_MEMBER', 'SUPERVISOR' Roles")
    @GetMapping(value = "/commission/search")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER','SUPERVISOR')")
    public PageResponse<ApplicationOperatorDTO> searchApplicationForCommission(SearchApplicationDTO search) {
        return certificationApplicationService.findAllForCommission(search);
    }

    @Operation(summary = "Get preview of breeding animal farm's certificate")
    @GetMapping("/{certificationApplicationId}/certificate-preview")
    public Optional<BreedingAnimalFarmCertificatePreviewDTO> getCertificatePreview(
            @PathVariable("certificationApplicationId") Integer certificationApplicationId) {
        return breedingAnimalFarmCertificateService.findPreviewByCertificationApplicationId(certificationApplicationId);
    }

    @Operation(summary = "Check permission for new application request")
    @GetMapping("/{farmId}")
    public boolean hasPermissionForNewApplication(
            @PathVariable("farmId") Integer farmId) {
        return certificationApplicationService.hasPermissionForNewApplication(farmId);
    }

    @Operation(summary = "Delete a certification application")
    @DeleteMapping("/{certificationApplicationId}")
    public void deleteById(@PathVariable("certificationApplicationId") Integer certificationApplicationId) {
        certificationApplicationService.deleteById(certificationApplicationId);
    }

    @Operation(summary = "Count delayed certification application decision")
    @GetMapping("/count-delayed-application-decision")
    public Integer countDelayedApplication() {
        return certificationApplicationService.countDelayedApplication();
    }

    @Operation(summary = "Create Certification Application By Farmer")
    @PostMapping
    // todo: @PreAuthorize("hasAnyAuthority('FARMER')")
    public void createApplication(HttpServletRequest request, @Valid @RequestBody NewApplicationDTO newApplication) {
        certificationApplicationService.create(request.getHeader("SID"), newApplication);
    }

    @Operation(summary = "Get Application by id for FARMER")
    @GetMapping("/farmer/{id}/{farmId}")
    // todo: @PreAuthorize("hasAnyAuthority('FARMER')")
    public ApplicationDTO getApplicationByIdForFarmer(@PathVariable Integer id, @PathVariable Integer farmId) {
        return applicationDataService.getApplicationByIdForFarmer(id, farmId);
    }

    @Operation(summary = "Get Application by id for INSPECTOR")
    @GetMapping("/inspector/{id}")
    @PreAuthorize("hasAnyAuthority('INSPECTOR')")
    public ApplicationDTO getApplicationByIdForInspector(@PathVariable Integer id) {
        return applicationDataService.getApplicationByIdForInspector(id);
    }

    @Operation(summary = "Get Application by id for SUPERVISOR, COMMISSION_CHAIRMAN and COMMISSION_MEMBER")
    @GetMapping("/commission/{id}")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER','SUPERVISOR')")
    public ApplicationDTO getApplicationByIdForCommission(@PathVariable Integer id) {
        return applicationDataService.getApplicationByIdForCommission(id);
    }
}
