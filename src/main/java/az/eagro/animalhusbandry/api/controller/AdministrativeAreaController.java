package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.AdministrativeAreaService;
import az.eagro.animalhusbandry.api.service.model.AdministrativeAreaDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administrative-area")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Administrative Area Controller")
public class AdministrativeAreaController {

    private final AdministrativeAreaService areaService;

    @Operation(summary = "Get Administrative Areas by RegionId")
    @GetMapping
    public List<AdministrativeAreaDTO> getAdministrativeAreas(@RequestParam("regionId") Integer regionId) throws BusinessException {
        return areaService.getAdministrativeAreas(regionId);
    }

}
