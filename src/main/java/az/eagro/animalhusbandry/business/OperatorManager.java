package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.UserRole;
import az.eagro.animalhusbandry.repository.OperatorRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OperatorManager {

    private final OperatorRepository operatorRepository;
    private final AuthenticatedUserInfoProvider auth;

    public OperatorEntity getByPin(String pin) {
        return operatorRepository.findByPersonalIdentificationNumber(pin).orElse(null);
    }

    public List<UserRole> getOperatorRoles() {
        return operatorRepository.findByPersonalIdentificationNumber(auth.getUserPin()).get().getGrantedAuthorities().stream()
                .map(grantedAuthorityEntity -> grantedAuthorityEntity.getRole()).collect(Collectors.toList());
    }

}
