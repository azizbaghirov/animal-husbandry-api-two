package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FieldService;
import az.eagro.animalhusbandry.api.service.model.FieldAssetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asset-input-configuration")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Norm Controller")
public class AssetConfigurationController {

    private final FieldService fieldService;

    @Operation(summary = "Get AssetInputConfiguration List by FarmType and FarmSpecialization")
    @GetMapping("/{farmTypeId}/{farmSpecializationId}")
    public List<FieldAssetDTO> getAssetsByFarmTypeIdAndFarmSpecializationId(
            @PathVariable("farmTypeId") Integer farmTypeId,
            @PathVariable(value = "farmSpecializationId", required = false) Integer farmSpecializationId) {
        return fieldService.getAssetsByFarmTypeIdAndFarmSpecializationId(farmTypeId, farmSpecializationId);
    }
}
