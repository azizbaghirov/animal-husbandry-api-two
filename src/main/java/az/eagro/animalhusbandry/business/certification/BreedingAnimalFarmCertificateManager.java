package az.eagro.animalhusbandry.business.certification;

import az.eagro.animalhusbandry.api.service.DocumentTypeService;
import az.eagro.animalhusbandry.api.service.FileService;
import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import az.eagro.animalhusbandry.business.CertificationApplicationAccessValidator;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.DocumentTypeLabel;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.FileType;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.certification.BreedingAnimalFarmCertificateEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.repository.CertificationApplicationRepository;
import az.eagro.animalhusbandry.repository.certification.BreedingAnimalFarmCertificateRepository;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BreedingAnimalFarmCertificateManager {

    private final BreedingAnimalFarmCertificateRepository certificateRepository;
    private final CertificationApplicationRepository applicationRepository;
    private final CertificationApplicationAccessValidator accessValidator;
    private final DocumentTypeService documentTypeService;
    private final FileService fileService;
    private final PdfGenerator pdfGenerator;

    public Optional<BreedingAnimalFarmCertificateEntity> findByIdCertificationApplication(Integer certificationApplicationId) {
        accessValidator.validateAccess(certificationApplicationId);

        return certificateRepository.findByCertificationApplicationId(certificationApplicationId);
    }

    public void createCertificate(FinalMonitoringDecisionEntity decision) throws IOException {
        CertificationApplicationEntity application = applicationRepository.findById(decision.getCertificationApplicationId()).get();
        String registrationNumber = String.format("D%d", certificateRepository.getNextValRegisterNumberSequence());

        BreedingAnimalFarmCertificateEntity newFarmCertificate = BreedingAnimalFarmCertificateEntity.builder()
                .certificationApplicationId(application.getId())
                .finalMonitoringDecisionId(decision.getId())
                .registrationNumber(registrationNumber)
                .applicationNumber(application.getApplicationNumber())
                .activityType(application.getFarm().getActivityType())
                .farmerAccount(application.getFarmer())
                .farmType(application.getFarmType())
                .farmSpecialization(application.getFarm().getFarmSpecialization())
                .region(application.getFarm().getRegion())
                .administrativeArea(application.getFarm().getAdministrativeArea())
                .certificationDate(decision.getRegisteredAt().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();

        StringBuilder farmer = new StringBuilder();

        getFarmSubject(newFarmCertificate, farmer);

        OperatorEntity operatorEntity = decision.getCreatedBy();
        StringBuilder operator = new StringBuilder();
        operator.append(operatorEntity.getSurname())
                .append(' ')
                .append(operatorEntity.getName())
                .append(' ')
                .append(operatorEntity.getPatronymic());

        createRegistryExcerptFile(operator.toString(), application, newFarmCertificate, farmer.toString());

        certificateRepository.save(newFarmCertificate);
    }

    private void getFarmSubject(BreedingAnimalFarmCertificateEntity newFarmCertificate, StringBuilder farmer) {
        if (newFarmCertificate.getFarmerAccount().getLegalPerson() != null) {
            farmer.append(newFarmCertificate.getFarmerAccount().getLegalPerson().getName());
        } else {
            var person = newFarmCertificate.getFarmerAccount().getPhysicalPerson();
            farmer.append(person.getSurname()).append(' ').append(person.getName()).append(' ').append(person.getPatronymic());
        }
    }

    private void createRegistryExcerptFile(String operator, CertificationApplicationEntity application,
            BreedingAnimalFarmCertificateEntity newFarmCertificate, String farmer) throws IOException {

        Integer registryDocumentTypeId = documentTypeService.getDocumentTypeByLabel(DocumentTypeLabel.REGISTRY_EXCERPT.name()).getId();

        byte[] pdfBytes = pdfGenerator.generate(newFarmCertificate, application, farmer, operator);
        String content = Base64.getEncoder().encodeToString(pdfBytes);

        NewFileDTO fileDTO = NewFileDTO.builder().content(content).documentTypeId(registryDocumentTypeId)
                .contentType(MediaType.APPLICATION_PDF_VALUE).originalFilename("registry-document.pdf").build();

        FileEntity file = fileService.getFile(fileDTO, FileType.BREEDING_ANIMAL_FARM_CERTIFICATE);
        newFarmCertificate.setRegistryFile(file);
    }

    public void deleteCertificate(FinalMonitoringDecisionEntity decision) {
        BreedingAnimalFarmCertificateEntity certificateFarm = certificateRepository
                .findByCertificationApplicationId(decision.getCertificationApplicationId()).get();
        certificateFarm.setInvalidatedAt(decision.getInvalidatedAt());
        certificateRepository.save(certificateFarm);

    }

}
