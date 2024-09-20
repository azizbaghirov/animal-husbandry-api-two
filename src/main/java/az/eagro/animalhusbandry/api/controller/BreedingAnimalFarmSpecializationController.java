package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FarmSpecializationService;
import az.eagro.animalhusbandry.api.service.model.FarmSpecializationDTO;
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
@RequestMapping("/api/farm-specialization")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Farm Specialization Controller")
public class BreedingAnimalFarmSpecializationController {

    private final FarmSpecializationService farmSpecializationService;

    @Operation(summary = "Get Farm Specialization List by FarmTypeId and AnimalTypeId")
    @GetMapping("/{farmTypeId}/{animalTypeId}")
    public List<FarmSpecializationDTO> farmRequest(
            @PathVariable("farmTypeId") Integer farmTypeId, @PathVariable("animalTypeId") Integer animalTypeId) {
        return farmSpecializationService.getFarmSpecializationByFarmTypeIdAndAnimalTypeId(farmTypeId, animalTypeId);
    }

}
