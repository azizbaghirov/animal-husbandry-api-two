package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.business.FarmerAccountManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmerAccountService {

    private final FarmerAccountManager farmerAccountManager;

    public void checkoutFarmerAccount() {
        farmerAccountManager.checkoutFarmerAccount();
    }

}
