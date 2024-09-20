package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FieldDocumentService;
import az.eagro.animalhusbandry.api.service.model.DocumentRequestDTO;
import az.eagro.animalhusbandry.api.service.model.FieldDocumentSummaryDTO;
import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import az.eagro.animalhusbandry.model.FieldDocumentTypeLabel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/field-document")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Field Document Controller")
public class FieldDocumentController {

    private final FieldDocumentService fieldDocumentService;

    @Operation(summary = "Farm field documents by area")
    @GetMapping("/list-by-area-id/{areaId}")
    public Set<FieldDocumentDTO> getFieldDocumentsByAreaId(@PathVariable Integer areaId, DocumentRequestDTO document) {
        return fieldDocumentService.getFieldDocuments(areaId, document);
    }

    @Operation(summary = "Farm field documents by farmId (for farmer)")
    @GetMapping("/farm/{farmId}")
    public List<FieldDocumentSummaryDTO> getFieldDocumentsByFarmId(@PathVariable Integer farmId) {
        return fieldDocumentService.getFieldDocumentListByFarmId(farmId);
    }

    @Operation(summary = "Field document types")
    @GetMapping("/types")
    public List<Map<String, String>> getFieldDocumentTypes() {
        return Arrays.stream(FieldDocumentTypeLabel.values())
                .map(type -> Map.of("label", type.getLabel(), "name", type.getName()))
                .collect(Collectors.toList());

    }

}
