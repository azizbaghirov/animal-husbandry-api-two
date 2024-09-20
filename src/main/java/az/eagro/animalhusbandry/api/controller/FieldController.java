package az.eagro.animalhusbandry.api.controller;


import az.eagro.animalhusbandry.api.service.FieldService;
import az.eagro.animalhusbandry.api.service.model.FieldDTO;
import az.eagro.animalhusbandry.api.service.model.FieldSummaryDTO;
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
@RequestMapping("/api/field")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Field Controller")
public class FieldController {

    private final FieldService fieldService;

    @Operation(summary = "Get fields")
    @GetMapping("/farm/{id}")
    public List<FieldSummaryDTO> getFieldsByFarmId(@PathVariable Integer id) {
        return fieldService.getFieldsByFarmId(id);
    }

}