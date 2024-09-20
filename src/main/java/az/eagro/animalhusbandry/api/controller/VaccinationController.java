package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.VaccinationService;
import az.eagro.animalhusbandry.api.service.model.VaccinationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vaccination")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Vaccination Controller")
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @Operation(summary = "Get All Vaccinations")
    @GetMapping("/all")
    public List<VaccinationDTO> getVaccinationsByFarmTpeId(@RequestParam("farmTypeId") Integer farmTypeId) {
        return vaccinationService.getVaccinationsByFarmTypeId(farmTypeId);
    }

}
