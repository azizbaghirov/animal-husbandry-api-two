package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application-status")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "ApplicationStatus Controller")
public class ApplicationStatusController {

    @Operation(summary = "Application Status Labels")
    @GetMapping("/labels")
    public List<ApplicationStatus> getApplicationStatusLabels() {
        List<ApplicationStatus> labels = Arrays.stream(ApplicationStatus.values()).collect(Collectors.toList());
        return labels;
    }

}
