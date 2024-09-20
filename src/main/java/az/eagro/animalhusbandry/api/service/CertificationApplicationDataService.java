package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.ApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.CertificationApplicationDataDTO;
import az.eagro.animalhusbandry.api.service.model.FieldValueDTO;
import az.eagro.animalhusbandry.business.CertificationApplicationDataManager;
import az.eagro.animalhusbandry.model.CertificationApplicationDataEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import com.remondis.remap.Mapper;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationDataService {

    private final CertificationApplicationDataManager applicationDataManager;
    private final Mapper<CertificationApplicationDataEntity, CertificationApplicationDataDTO> certificationApplicationDataToDtoMapper;
    private final Mapper<CertificationApplicationEntity, ApplicationDTO> applicationToDtoMapper;
    private final Mapper<FieldValueEntity, FieldValueDTO> fieldValueToDtoMapper;


    public CertificationApplicationDataDTO getByApplicationIdAndIsRegistered(Integer id) {
        return certificationApplicationDataToDtoMapper.map(applicationDataManager.getByApplicationIdAndIsRegistered(id));
    }

    public ApplicationDTO getApplicationByIdForFarmer(Integer id, Integer farmId) {
        var appData = applicationDataManager.findByIdForFarmer(id, farmId);
        return getApplicationDTO(appData);
    }

    public ApplicationDTO getApplicationByIdForInspector(Integer id) {
        var appData = applicationDataManager.findByIdForInspector(id);
        return getApplicationDTO(appData);
    }

    public ApplicationDTO getApplicationByIdForCommission(Integer id) {
        var appData = applicationDataManager.findByIdForCommission(id);
        return getApplicationDTO(appData);
    }

    private ApplicationDTO getApplicationDTO(CertificationApplicationDataEntity appData) {
        var applicationDTO = applicationToDtoMapper.map(appData.getApplication());
        var fields = fieldValueToDtoMapper.map(appData.getFieldValues());
        applicationDTO.setFieldValues(new HashSet<>(fields));
        return applicationDTO;
    }

}
