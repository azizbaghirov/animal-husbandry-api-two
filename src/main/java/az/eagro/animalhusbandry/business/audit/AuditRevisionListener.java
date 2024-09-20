package az.eagro.animalhusbandry.business.audit;

import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import az.eagro.animalhusbandry.model.RevisionInfoEntity;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(onConstructor = @__(@Lazy))
public class AuditRevisionListener implements RevisionListener {

    @Autowired
    private AuthenticatedUserInfoProvider authenticatedUserInfoProvider;

    @Override
    public void newRevision(Object obj) {
        RevisionInfoEntity revInfo = (RevisionInfoEntity) obj;
        UUID userId = authenticatedUserInfoProvider.getUserId();
        revInfo.setUserId(userId);
    }
}
