package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.PageResponse;
import az.eagro.animalhusbandry.api.service.model.ApplicationOperatorDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.CertificationApplicationStateDTO;
import az.eagro.animalhusbandry.api.service.model.FindApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.NewApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.NewFieldValueDTO;
import az.eagro.animalhusbandry.api.service.model.SearchApplicationDTO;
import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import az.eagro.animalhusbandry.util.fileutil.FileManager;
import com.remondis.remap.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationService {

    private final CertificationApplicationManager certificationApplicationManager;
    private final Mapper<CertificationApplicationEntity, ApplicationSummaryDTO> applicationSummaryModelDTOMapper;
    private final Mapper<CertificationApplicationEntity, ApplicationOperatorDTO> applicationOperatorModelDTOMapper;
    private final Mapper<NewApplicationDTO, CertificationApplicationEntity> newApplicationModelMapper;
    private final Mapper<NewFieldValueDTO, FieldValueEntity> newFieldValueModelMapper;
    private final FileManager fileManager;

    public void create(String token, NewApplicationDTO newApplicationDTO) {
        var newApplication = newApplicationModelMapper.map(newApplicationDTO);
        var newFieldValues = newFieldValueModelMapper.map(newApplicationDTO.getFieldValues());
        fileManager.saveBreedingAnimalFile(newApplication, newApplicationDTO.getFile());
        certificationApplicationManager.create(token, newApplication, newFieldValues, newApplicationDTO.isSelectedAll(),
                newApplicationDTO.getNicknameOrTagId(), newApplicationDTO.getIgnoredIds(), newApplicationDTO.getMarkedIds());
    }

    public PageResponse<ApplicationSummaryDTO> findAllForFarmer(Integer farmTypeId, Integer farmId, FindApplicationDTO findApplicationDTO) {
        Pageable pageable = PageRequest.of(findApplicationDTO.getPage(), findApplicationDTO.getSize());
        var applications = certificationApplicationManager
                .findAllForFarmer(farmTypeId, farmId, findApplicationDTO, pageable);
        return new PageResponse<>(applicationSummaryModelDTOMapper.map(applications.getContent()), applications.getTotalPages(),
                applications.getTotalElements());
    }

    public PageResponse<ApplicationOperatorDTO> findAllForInspector(SearchApplicationDTO search) {
        Pageable pageable = PageRequest.of(search.getPage(), search.getSize());
        var applications = certificationApplicationManager.findAllForInspector(
                search.getFarmTypeId(), search.getPinTax(), search.getRegionId(), search.getCreatedAt(),
                search.getApplicationNumber(), search.getApplicationStatus(), search.getInitialMonitoringStatus(),
                search.getFinalMonitoringStatus(), pageable);
        return new PageResponse<>(applicationOperatorModelDTOMapper.map(applications.getContent()), applications.getTotalPages(),
                applications.getTotalElements());
    }

    public PageResponse<ApplicationOperatorDTO> findAllForCommission(SearchApplicationDTO search) {
        Pageable pageable = PageRequest.of(search.getPage(), search.getSize());
        var applications = certificationApplicationManager.findAllForCommission(
                search.getFarmTypeId(), search.getPinTax(), search.getRegionId(), search.getCreatedAt(),
                search.getApplicationNumber(), search.getApplicationStatus(), search.getInitialMonitoringStatus(),
                search.getFinalMonitoringStatus(), pageable);
        return new PageResponse<>(applicationOperatorModelDTOMapper.map(applications.getContent()), applications.getTotalPages(),
                applications.getTotalElements());
    }

    public CertificationApplicationStateDTO getCertificationApplicationState(Integer certificationApplicationId) {
        CertificationApplicationStateDTO state = certificationApplicationManager.getCertificationApplicationState(certificationApplicationId);
        return state;
    }

    public boolean hasPermissionForNewApplication(Integer farmId) {
        return certificationApplicationManager.hasPermissionForNewApplication(farmId);
    }

    public void deleteById(Integer certificationApplicationId) {
        certificationApplicationManager.deleteById(certificationApplicationId);
    }

    public Integer countDelayedApplication() {
        return certificationApplicationManager.countDelayedApplication();
    }

}

