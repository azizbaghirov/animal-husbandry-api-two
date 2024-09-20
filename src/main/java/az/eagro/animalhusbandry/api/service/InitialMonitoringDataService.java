package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.InitialMonitoringDataDTO;
import az.eagro.animalhusbandry.api.service.model.InitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.NewInitialMonitoringDataDTO;
import az.eagro.animalhusbandry.business.InitialMonitoringDataManager;
import az.eagro.animalhusbandry.business.InitialMonitoringDecisionManager;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.FileType;
import az.eagro.animalhusbandry.model.InitialMonitoringDataEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDecisionEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDataService {

    private final InitialMonitoringDataManager initialMonitoringDataManager;
    private final InitialMonitoringDecisionManager initialMonitoringDecisionManager;
    private final Mapper<InitialMonitoringDecisionEntity, InitialMonitoringDecisionDTO> initialMonitoringDecisionToDtoMapper;
    private final Mapper<NewInitialMonitoringDataDTO, InitialMonitoringDataEntity> newInitialMonitoringDataModelMapper;
    private final FileService fileService;
    private final Mapper<InitialMonitoringDataEntity, InitialMonitoringDataDTO> initialMonitoringDataToDtoMapper;
    private final VaccinationService vaccinationService;

    public void createInitialMonitoringData(NewInitialMonitoringDataDTO newInitialMonitoringData) {
        var initialMonitoringDataEntity = newInitialMonitoringDataModelMapper.map(newInitialMonitoringData);
        var files = newInitialMonitoringData.getMonitoringDecision().getFiles();
        List<FileEntity> fileList = fileService.getFiles(files, FileType.INITIAL_MONITORING_DECISION);
        initialMonitoringDataEntity.getMonitoringDecision().setFiles(fileList);
        initialMonitoringDataManager.createInitialMonitoringData(initialMonitoringDataEntity);
    }

    public InitialMonitoringDataDTO getByApplicationIdForFarmer(Integer applicationId) {
        var imd = initialMonitoringDecisionManager.getByApplicationIdForFarmer(applicationId);
        return getInitialMonitoringDataDTO(imd);
    }

    public InitialMonitoringDataDTO getByApplicationIdForInspector(Integer applicationId) {
        var imd = initialMonitoringDecisionManager.getByApplicationIdForInspector(applicationId);
        return getInitialMonitoringDataDTO(imd);
    }

    public InitialMonitoringDataDTO getByApplicationIdForCommission(Integer applicationId) {
        var imd = initialMonitoringDecisionManager.getByApplicationIdForCommission(applicationId);
        return getInitialMonitoringDataDTO(imd);
    }

    private InitialMonitoringDataDTO getInitialMonitoringDataDTO(Optional<InitialMonitoringDecisionEntity> imd) {
        InitialMonitoringDataDTO dataDTO = null;
        if (imd.isPresent()) {
            InitialMonitoringDecisionDTO decisionDTO = imd.map(initialMonitoringDecisionToDtoMapper::map).get();
            dataDTO = initialMonitoringDataToDtoMapper.map(initialMonitoringDataManager.getByDecisionId(decisionDTO.getId()));
            var vaccinations = vaccinationService.getByInitMonitoringDataId(dataDTO.getId());
            dataDTO.setVaccinations(vaccinations);
            dataDTO.setMonitoringDecision(decisionDTO);
        }
        return dataDTO;
    }


    public void deleteById(Integer initialMonitoringDecisionId) {
        initialMonitoringDecisionManager.deleteById(initialMonitoringDecisionId);
    }
}
