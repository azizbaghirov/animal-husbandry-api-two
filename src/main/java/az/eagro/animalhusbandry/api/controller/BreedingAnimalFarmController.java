package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.PageResponse;
import az.eagro.animalhusbandry.api.service.BreedingAnimalFarmService;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmDTO;
import az.eagro.animalhusbandry.api.service.model.FarmAuditDTO;
import az.eagro.animalhusbandry.api.service.model.FindBreedingAnimalFarmDTO;
import az.eagro.animalhusbandry.api.service.model.NewFarmDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/farm")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "BreedingAnimalFarm Controller")
public class BreedingAnimalFarmController {

    private final BreedingAnimalFarmService farmService;

    @Operation(summary = "Get Farm list")
    @GetMapping
    public PageResponse<BreedingAnimalFarmDTO> getFarmList(FindBreedingAnimalFarmDTO farmDTO) {
        return farmService.getFarmListByRegionIdAndFarmTypeId(farmDTO);
    }

    @Operation(summary = "Add Farm")
    @PostMapping
    public void createFarm(@Valid @RequestBody NewFarmDTO farmDTO) {
        farmService.createFarm(farmDTO);
    }

    @Operation(summary = "Delete Farm")
    @DeleteMapping("/{id}")
    public void deleteFarm(@PathVariable Integer id) {
        farmService.deleteFarm(id);
    }

    @Operation(summary = "History of Activity Status")
    @GetMapping("/{id}/activity-status-history")
    public List<FarmAuditDTO> getAuditedFarmList(@PathVariable Integer id) {
        return farmService.getAuditedFarms(id);
    }

    @Operation(summary = "Get Farm By ID")
    @GetMapping("/{id}")
    public BreedingAnimalFarmDTO getFarmById(@PathVariable Integer id) {
        return farmService.getById(id);
    }

    @Operation(summary = "Check Farm By Area And Farm Specification ")
    @GetMapping("/check/area/{areaId}/farm-specialization/{farmSpecializationId}")
    public void checkFarmByAreaAndFarmSpecialisation(@PathVariable Integer areaId, @PathVariable Integer farmSpecializationId) {
        farmService.checkFarmByAreaAndSpecialization(areaId, farmSpecializationId);
    }

    @Operation(summary = "Exists Farm")
    @GetMapping("/exists/{farmId}")
    public Boolean existsFarm(@PathVariable("farmId") Integer farmId) {
        return farmService.existsFarmByFarmId(farmId);
    }

}
