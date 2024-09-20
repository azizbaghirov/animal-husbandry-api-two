package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.GrantedAuthorityEntity;
import az.eagro.animalhusbandry.model.UserRole;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMemberEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationCommissionMemberValidation {

    public void ensureCommissionGroupIsValid(List<CertificationCommissionMemberEntity> allMembers, List<UserRole> roles) {

        List<UserRole> userRoles = allMembers.stream()
                .peek(commissionMember -> {
                    if (commissionMember.getOperator().getGrantedAuthorities().stream()
                            .map(GrantedAuthorityEntity::getRole).collect(Collectors.toList()).containsAll(roles)) {
                        throw new BusinessException("Bir istifadəçi həm komissiya üzvü həm də komissiya rəhbəri ola bilməz. ");
                    }
                })
                .flatMap(commissionMember -> commissionMember.getOperator().getGrantedAuthorities().stream())
                .map(GrantedAuthorityEntity::getRole).toList();

        if (Collections.frequency(userRoles, UserRole.COMMISSION_CHAIRMAN) != 1) {
            throw new BusinessException("Komissiya rəhbəri mövcud deyil ya da 1-dən çoxdur. ");
        }

    }
}
