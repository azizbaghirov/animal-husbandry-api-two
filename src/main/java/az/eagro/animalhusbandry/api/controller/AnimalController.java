package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.PageRequest;
import az.eagro.animalhusbandry.api.PageResponse;
import az.eagro.animalhusbandry.api.service.AnimalService;
import az.eagro.animalhusbandry.feign.model.AnimalStatusDTO;
import az.eagro.animalhusbandry.feign.model.AnimalSummaryDTO;
import az.eagro.animalhusbandry.feign.model.FindAnimalDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Animal Controller")
public class AnimalController {

    private final AnimalService animalService;

    @Operation(summary = "Get Animals by farmId ")
    @GetMapping("/list/{farmId}")
    public PageResponse<AnimalSummaryDTO> getAnimals(
            HttpServletRequest request, @PathVariable(value = "farmId") Integer farmId, FindAnimalDTO findAnimal) {
        return animalService.getAnimals(request.getHeader("SID"), farmId, findAnimal);
    }

    @Operation(summary = "Get Animals by applicationId ")
    @GetMapping("/list-by-application-id/{applicationId}")
    public PageResponse<AnimalStatusDTO> getAnimalsByIds(
            HttpServletRequest request, @PathVariable(value = "applicationId") Integer applicationId, PageRequest pageRequest) {
        return animalService.getAnimalsByApplicationId(request.getHeader("SID"), applicationId, pageRequest);
    }
}
