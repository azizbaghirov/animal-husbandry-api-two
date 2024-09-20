package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.AnimalTypeService;
import az.eagro.animalhusbandry.api.service.model.AnimalTypeDTO;
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
@RequestMapping("/api/animal-type")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Animal Type Controller")
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @Operation(summary = "Get AnimalType List by farmTypeId")
    @GetMapping("/list/{farmTypeId}")
    public List<AnimalTypeDTO> getAnimalTypesByFarmTypeId(@PathVariable("farmTypeId") Integer farmTypeId) {
        return animalTypeService.getAnimalTypesByFarmTypeId(farmTypeId);
    }
}
