package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FarmerAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check-farmer-account")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "Check Authenticated Farmer Account Controller")
public class CheckAuthFarmerController {

    private final FarmerAccountService farmerAccountService;

    @Operation(summary = "Check Farmer Account")
    @GetMapping
    public void checkFarmerAccount() {
        farmerAccountService.checkoutFarmerAccount();
    }

}
