package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.DocumentTypeService;
import az.eagro.animalhusbandry.feign.model.DocumentTypeDTO;
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
@RequestMapping("/api/document-type")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Document Type Controller")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @Operation(summary = "Get initial monitoring document types")
    @GetMapping("/initial-monitoring")
    public List<DocumentTypeDTO> getInitialMonitoringDocumentTypes() {
        return documentTypeService.getInitialMonitoringDocumentTypes();
    }

    @Operation(summary = "Get final monitoring decision document types")
    @GetMapping("/final-monitoring-decision")
    public List<DocumentTypeDTO> getFinalMonitoringDocumentTypes() {
        return documentTypeService. getFinalMonitoringDocumentTypes();
    }

}
