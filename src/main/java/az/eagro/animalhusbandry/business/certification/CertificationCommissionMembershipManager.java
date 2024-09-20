package az.eagro.animalhusbandry.business.certification;

import az.eagro.animalhusbandry.business.CertificationCommissionMemberValidation;
import az.eagro.animalhusbandry.model.UserRole;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMemberEntity;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMembershipEntity;
import az.eagro.animalhusbandry.repository.certification.CertificationCommissionMemberRepository;
import az.eagro.animalhusbandry.repository.certification.CertificationCommissionMembershipRepository;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationCommissionMembershipManager {

    private final CertificationCommissionMembershipRepository certificationCommissionMembershipRepository;
    private final CertificationCommissionMemberRepository certificationCommissionMemberRepository;
    private final CertificationCommissionMemberValidation certificationCommissionMemberValidation;

    public CertificationCommissionMembershipEntity getCurrentCertificationCommissionMembership() {
        int currentYear = Year.now().getValue();
        return certificationCommissionMembershipRepository.findMembershipByYear(currentYear).orElse(null);
    }

    // Todo: comissiya uzvlerinin siyahisini muraciet (tarixi) esasinda tertib etmek
    public List<CertificationCommissionMemberEntity> getCurrentCertificationCommissionMembers() {
        CertificationCommissionMembershipEntity currentMembership = getCurrentCertificationCommissionMembership();
        List<UserRole> roles = Arrays.asList(UserRole.COMMISSION_MEMBER, UserRole.COMMISSION_CHAIRMAN);
        List<CertificationCommissionMemberEntity> allMembers = certificationCommissionMemberRepository.findAllByMembership(currentMembership, roles);
        certificationCommissionMemberValidation.ensureCommissionGroupIsValid(allMembers, roles);
        return allMembers;
    }

}
