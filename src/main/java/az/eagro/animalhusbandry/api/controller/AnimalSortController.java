package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.AnimalSortService;
import az.eagro.animalhusbandry.api.service.model.AnimalSortDTO;
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
@RequestMapping("/api/animal-sort")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Animal Sort Controller")
public class AnimalSortController {

    private final AnimalSortService animalSortService;

    @Operation(summary = "Get AnimalSort List by animalTypeId")
    @GetMapping("/list/{animalTypeId}")
    public List<AnimalSortDTO> getAnimalSortsByAnimalTypeId(@PathVariable("animalTypeId") Integer animalTypeId) {
        return animalSortService.getAnimalSortsByAnimalTypeId(animalTypeId);
    }
}
