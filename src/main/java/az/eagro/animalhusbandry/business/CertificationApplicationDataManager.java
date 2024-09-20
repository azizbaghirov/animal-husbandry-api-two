package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationDataEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.repository.CertificationApplicationDataRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationDataManager {

    private final CertificationApplicationDataRepository applicationDataRepository;
    private final AuthenticatedUserInfoProvider auth;
    private final FarmerAccountManager farmer;

    @Transactional
    public CertificationApplicationDataEntity createCertificationApplicationData(CertificationApplicationDataEntity newApplicationData) {
        return applicationDataRepository.save(newApplicationData);
    }

    public CertificationApplicationDataEntity getByIdOrFarmerOrRegions(Integer id, UUID farmerId, List<Integer> regionIds, Integer farmId) {
        return applicationDataRepository.findByApplicationIdOrFarmerIdOrRegionIds(id, farmerId, regionIds, farmId)
                .orElseThrow(() -> new BusinessException("Müraciət tapılmadı"));
    }

    public CertificationApplicationDataEntity getByApplicationIdAndIsRegistered(Integer id) {
        List<Integer> regionIds = auth.getRegions().isEmpty() ? null : auth.getRegionIds();

        return applicationDataRepository
                .findByApplicationIdAndApplicationStatusOrRegionIds(id, ApplicationStatus.REGISTERED, regionIds)
                .orElseThrow(() -> new BusinessException("Müraciət tapılmadı. ApplicationId:" + id));
    }

    public CertificationApplicationDataEntity findByIdForFarmer(Integer id, Integer farmId) {
        var farmerId = farmer.findFarmerAccount().getId();
        return getApplicationByIdOrFarmerIdOrRegionIds(id, farmerId, null, farmId);
    }

    public CertificationApplicationDataEntity findByIdForInspector(Integer id) {
        List<Integer> regionIds = auth.getRegions().isEmpty() ? null : auth.getRegionIds();
        return getApplicationByIdOrFarmerIdOrRegionIds(id, null, regionIds, null);
    }

    public CertificationApplicationDataEntity findByIdForCommission(Integer id) {
        return getApplicationByIdOrFarmerIdOrRegionIds(id, null, null, null);
    }

    private CertificationApplicationDataEntity getApplicationByIdOrFarmerIdOrRegionIds(
            Integer id, UUID farmerId, List<Integer> regionIds, Integer farmId) {
        return getByIdOrFarmerOrRegions(id, farmerId, regionIds, farmId);
    }

}
