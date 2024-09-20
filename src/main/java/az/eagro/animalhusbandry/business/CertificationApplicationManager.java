package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.api.service.model.CertificationApplicationStateDTO;
import az.eagro.animalhusbandry.api.service.model.FindApplicationDTO;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationDataEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import az.eagro.animalhusbandry.repository.CertificationApplicationRepository;
import az.eagro.animalhusbandry.util.timeutil.TimeValidation;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationManager {

    private static final int TAX_PAYER_IDENTIFICATION_NUMBER_LENGTH = 10;

    private final CertificationApplicationRepository certificationApplicationRepository;
    private final FarmerAccountManager farmerAccountManager;
    private final AuthenticatedUserInfoProvider userInfoProvider;
    private final CertificationApplicationAccessValidator certificationApplicationAccessValidator;
    private final CertificationApplicationStateResolver certificationApplicationStateResolver;
    private final InitialMonitoringDecisionValidation initialMonitoringDecisionValidation;
    private final CertificationApplicationValidation certificationApplicationValidation;
    private final CertificationApplicationDataManager applicationDataManager;
    private final AnimalManager animalManager;

    @Transactional
    public void create(
            String token, CertificationApplicationEntity na, List<FieldValueEntity> newFieldValues, boolean selectedAll,
            String nicknameOrTagId, Set<Integer> ignoredIds, Set<Integer> markedIds) {
        var farmerAccount = farmerAccountManager.findFarmerAccount();
        na.setFarmer(farmerAccount);
        certificationApplicationValidation.newApplicationValidation(na, newFieldValues);

        Set<Integer> animalIds = animalManager.getAnimalIds(token, na.getFarm().getId(), nicknameOrTagId, selectedAll, ignoredIds, markedIds);

        na.setAnimalIds(animalIds);
        na.setApplicationNumber(String.format("P%d", certificationApplicationRepository.getNextValApplicationNumberSequence()));
        na.setFarmType(na.getFarm().getFarmType());

        CertificationApplicationDataEntity cade = CertificationApplicationDataEntity.builder().application(na).build();

        for (FieldValueEntity fieldValue : newFieldValues) {
            fieldValue.setApplicationData(cade);
        }
        cade.setFieldValues(newFieldValues);

        applicationDataManager.createCertificationApplicationData(cade);
    }

    public Optional<CertificationApplicationEntity> findById(Integer certificationApplicationId) {
        if (userInfoProvider.isCommissionChairmanOrMemberOrSupervisor()) {
            return certificationApplicationRepository.findById(certificationApplicationId);
        }

        if (userInfoProvider.isInspector()) {
            List<Integer> regionIds = userInfoProvider.getRegionIds();

            return CollectionUtils.isEmpty(regionIds)
                    ? certificationApplicationRepository.findById(certificationApplicationId)
                    : certificationApplicationRepository.findByIdAndRegionIds(certificationApplicationId, regionIds);
        }

        if (!userInfoProvider.isOperator()) {
            FarmerAccountEntity farmerAccount = farmerAccountManager.findFarmerAccount();

            return certificationApplicationRepository.findByIdAndFarmerAccount(certificationApplicationId, farmerAccount);
        }

        return Optional.empty();
    }

    public CertificationApplicationEntity getById(Integer certificationApplicationId) {
        return findById(certificationApplicationId)
                .orElseThrow(() -> new BusinessException("Certification application not found: " + certificationApplicationId));
    }

    public void update(CertificationApplicationEntity certificationApplication) {
        certificationApplicationRepository.save(certificationApplication);
    }

    public Page<CertificationApplicationEntity> findAllForFarmer(Integer farmTypeId, Integer farmId, FindApplicationDTO dto, Pageable pageable) {
        var farmerId = farmerAccountManager.findFarmerAccount().getId();
        return certificationApplicationRepository.findByFarmerAll(farmTypeId, farmId, farmerId, dto.getFarmSpecId(), dto.getAdministrativeArea(),
                dto.getAnimalSortId(), dto.getAnimalTypeId(), dto.getApplicationStatus(), pageable);
    }

    public Page<CertificationApplicationEntity> findAllForInspector(
            Integer farmTypeId, String taxPayerNumOrPin, Integer regionId, Instant createdAt,
            String applicationNumber, ApplicationStatus applicationStatus, InitialMonitoringStatus initialStatus,
            FinalMonitoringStatus finalStatus, Pageable pageable) {
        List<Integer> ids = userInfoProvider.getRegions().isEmpty() ? null : userInfoProvider.getRegionIds();
        return findAll(farmTypeId, ids, taxPayerNumOrPin, regionId, createdAt, applicationNumber, applicationStatus,
                initialStatus, finalStatus, pageable);

    }

    public Page<CertificationApplicationEntity> findAllForCommission(
            Integer farmTypeId, String taxPayerNumOrPin, Integer regionId, Instant createdAt,
            String applicationNumber, ApplicationStatus applicationStatus, InitialMonitoringStatus initialStatus,
            FinalMonitoringStatus finalStatus, Pageable pageable) {
        return findAll(farmTypeId, null, taxPayerNumOrPin, regionId, createdAt, applicationNumber, applicationStatus,
                initialStatus, finalStatus, pageable);

    }

    private Page<CertificationApplicationEntity> findAll(
            Integer farmTypeId, List<Integer> ids, String taxPayerNumOrPin, Integer regionId, Instant createdAt,
            String applicationNumber, ApplicationStatus applicationStatus, InitialMonitoringStatus initialStatus,
            FinalMonitoringStatus finalStatus, Pageable pageable) {
        Instant startOfDay = null;
        Instant endOfDay = null;
        if (createdAt != null) {
            ZonedDateTime date = createdAt.atZone(ZoneId.systemDefault());
            startOfDay = date.toLocalDate().atStartOfDay(date.getZone()).toInstant();
            endOfDay = date.toLocalDate().plusDays(1).atStartOfDay(date.getZone()).minusSeconds(1).toInstant();
        }

        String taxPin = StringUtils.trimToNull(taxPayerNumOrPin);
        String taxPayerNum = null;

        if (StringUtils.isNotEmpty(taxPin) && (taxPin.length() == TAX_PAYER_IDENTIFICATION_NUMBER_LENGTH)) {
            taxPayerNum = taxPin;
            taxPin = null;
        }

        return certificationApplicationRepository.findAll(
                farmTypeId, ids, taxPin, taxPayerNum, regionId, startOfDay, endOfDay,
                applicationNumber, applicationStatus, initialStatus, finalStatus, pageable);
    }

    public CertificationApplicationEntity findRegisteredApplicationById(Integer id) {
        var application = getById(id);
        if (!application.getApplicationStatus().equals(ApplicationStatus.REGISTERED)) {
            throw new BusinessException("Müraciət '" + ApplicationStatus.REGISTERED.getLabel() + "' statusunda deyil. ");
        }
        return application;
    }

    public CertificationApplicationStateDTO getCertificationApplicationState(Integer applicationId) {
        certificationApplicationAccessValidator.validateAccess(applicationId);

        return certificationApplicationStateResolver.resolve(applicationId);
    }

    public boolean hasPermissionForNewApplication(Integer farmId) {
        List<CertificationApplicationEntity> application = certificationApplicationRepository.findByFarmId(farmId);

        if (!application.isEmpty()) {
            if (application.stream().anyMatch(certificationApplication ->
                    !certificationApplication.getApplicationStatus().equals(ApplicationStatus.REJECTED))) {
                return false;
            }
        }
        return true;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteById(Integer certificationApplicationId) {
        certificationApplicationAccessValidator.validateAccess(certificationApplicationId);
        initialMonitoringDecisionValidation.existsInitialMonitoringDecisionByApplicationId(certificationApplicationId);
        CertificationApplicationEntity application = certificationApplicationRepository.findById(certificationApplicationId).get();
        TimeValidation.validate(application.getCreatedAt());
        application.getFarm().setDeletable(true);
        certificationApplicationRepository.save(application);
        certificationApplicationRepository.deleteCertificationApplication(certificationApplicationId);
    }

    public Integer countDelayedApplication() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        ZonedDateTime thirtyDaysAgo = currentDateTime.minusDays(30);
        return certificationApplicationRepository.countDelayedApplication(thirtyDaysAgo.toInstant());
    }

    public CertificationApplicationEntity getAnimalIdsByApplicationId(Integer applicationId) {
        return certificationApplicationRepository.getAnimalIdsByApplicationId(applicationId);
    }
}