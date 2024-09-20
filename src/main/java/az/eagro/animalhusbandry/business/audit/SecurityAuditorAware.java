package az.eagro.animalhusbandry.business.audit;

import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SecurityAuditorAware implements AuditorAware<PhysicalPersonEntity> {

    private final AuthenticatedUserInfoProvider auth;

    @Override
    public Optional<PhysicalPersonEntity> getCurrentAuditor() {
        return Optional.of(PhysicalPersonEntity.builder().id(auth.getUserId()).build());
    }
}
