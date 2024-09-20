package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.OperatorService;
import az.eagro.animalhusbandry.model.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operator")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Operator Controller")
public class OperatorController {

    private final OperatorService operatorService;

    @Operation(summary = "Get All Operator Roles ")
    @GetMapping("/roles")
    @PreAuthorize("hasAnyAuthority('COMMISSION_CHAIRMAN','COMMISSION_MEMBER','INSPECTOR','SUPERVISOR')")
    public List<UserRole> getOperatorRoles() {
        return operatorService.getOperatorRoles();
    }
}
