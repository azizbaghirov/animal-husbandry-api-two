package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.NewCertificateFileDTO;
import az.eagro.animalhusbandry.api.service.model.NewDeniedFinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import az.eagro.animalhusbandry.api.service.model.NewPermittedFinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionRegistrationManager;
import az.eagro.animalhusbandry.model.DocumentTypeLabel;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.FileType;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionRegistrationService {

    private final FinalMonitoringDecisionRegistrationManager finalMonitoringDecisionRegistrationManager;
    private final Mapper<FinalMonitoringDecisionEntity, FinalMonitoringDecisionDTO> finalMonitoringDecisionModelDTOMapper;
    private final Mapper<NewPermittedFinalMonitoringDecisionDTO, FinalMonitoringDecisionEntity> newFinalMonitoringDecisionModelDTOMapper;
    private final Mapper<NewDeniedFinalMonitoringDecisionDTO, FinalMonitoringDecisionEntity> newDeniedFinalMonitoringDecisionModelDTOMapper;
    private final FileService fileService;
    private final DocumentTypeService documentTypeService;

    public FinalMonitoringDecisionDTO createPermittedDecision(NewPermittedFinalMonitoringDecisionDTO newPermitFinalMonitoringDecisionDTO) {
        FinalMonitoringDecisionEntity newDecision = newFinalMonitoringDecisionModelDTOMapper.map(newPermitFinalMonitoringDecisionDTO);

        collectNewFiles(newPermitFinalMonitoringDecisionDTO);

        List<FileEntity> files = fileService.getFiles(newPermitFinalMonitoringDecisionDTO.getFiles(), FileType.FINAL_MONITORING_DECISION);
        newDecision.setFiles(files);
        FinalMonitoringDecisionEntity finalMonitoringDecision = finalMonitoringDecisionRegistrationManager.createPermittedDecision(newDecision);

        return finalMonitoringDecisionModelDTOMapper.map(finalMonitoringDecision);
    }

    private void collectNewFiles(NewPermittedFinalMonitoringDecisionDTO newPermitFinalMonitoringDecisionDTO) {
        Integer certificateDocumentTypeId = documentTypeService.getDocumentTypeByLabel(DocumentTypeLabel.CERTIFICATE.name()).getId();

        NewCertificateFileDTO newCertificateFile = newPermitFinalMonitoringDecisionDTO.getCertificateFile();
        NewFileDTO newFile = NewFileDTO.builder()
                .content(newCertificateFile.getContent())
                .contentType(newCertificateFile.getContentType())
                .originalFilename(newCertificateFile.getOriginalFilename())
                .documentTypeId(certificateDocumentTypeId).build();

        newPermitFinalMonitoringDecisionDTO.getFiles().add(newFile);
    }

    public FinalMonitoringDecisionDTO createDeniedDecision(NewDeniedFinalMonitoringDecisionDTO newDeFinalMonitoringDecisionDTO) {
        FinalMonitoringDecisionEntity newDecision = newDeniedFinalMonitoringDecisionModelDTOMapper.map(newDeFinalMonitoringDecisionDTO);
        FinalMonitoringDecisionEntity deniedDecision = finalMonitoringDecisionRegistrationManager.createDeniedDecision(newDecision);

        return finalMonitoringDecisionModelDTOMapper.map(deniedDecision);
    }
}
