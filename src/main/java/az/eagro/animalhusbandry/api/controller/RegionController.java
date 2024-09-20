package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.RegionService;
import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Region Controller")
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "Get All Regions")
    @GetMapping("/all")
    public List<RegionDTO> getRegions() throws BusinessException {
        return regionService.getRegions();
    }

}
