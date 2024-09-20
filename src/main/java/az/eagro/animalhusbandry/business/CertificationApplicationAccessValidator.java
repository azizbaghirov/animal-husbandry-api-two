package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.RegionEntity;
import az.eagro.animalhusbandry.repository.CertificationApplicationRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationAccessValidator {

    private final CertificationApplicationRepository certificationApplicationRepository;
    private final AuthenticatedUserInfoProvider userInfoProvider;

    public void validateAccess(Integer certificationApplicationId) {
        CertificationApplicationEntity certificationApplication = certificationApplicationRepository.findById(certificationApplicationId)
                .orElseThrow(() -> new BusinessException("Certification application not found"));

        if (userInfoProvider.isCommissionChairmanOrMemberOrSupervisor()) {
            return;
        }

        if (userInfoProvider.isInspector()) {
            List<Integer> regionIds = userInfoProvider.getRegionIds();
            RegionEntity region = certificationApplication.getFarm().getRegion();

            if (regionIds.isEmpty() || CollectionUtils.containsAny(regionIds, region.getId())) {
                return;
            }
        }

        UUID authenticatedUserId = userInfoProvider.getUserId();
        FarmerAccountEntity applicationOwner = certificationApplication.getFarmer();
        UUID farmerId = applicationOwner.getPhysicalPerson() != null ? applicationOwner.getPhysicalPerson().getId() : null;
        if (Objects.equals(authenticatedUserId, farmerId)) {
            return;
        }

        throw new BusinessException("Unauthorized access CertificationApplication id=" + certificationApplicationId);
    }

}
