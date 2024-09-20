package az.eagro.animalhusbandry.api.service.mapper.certification;

import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmCertificatePreviewDTO;
import az.eagro.animalhusbandry.model.certification.BreedingAnimalFarmCertificateEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BreedingAnimalFarmCertificateModelDTOMapperConfig {

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<BreedingAnimalFarmCertificateEntity, BreedingAnimalFarmCertificatePreviewDTO> breedingAnimalFarmCertificatePreviewModelDTOMapper() {
        return Mapping.from(BreedingAnimalFarmCertificateEntity.class)
                .to(BreedingAnimalFarmCertificatePreviewDTO.class)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getCertificationApplicationId)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getFinalMonitoringDecisionId)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getFarmType)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getFarmSpecialization)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getRegion)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getAdministrativeArea)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getInvalidatedAt)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getFarmerAccount)
                .omitInSource(BreedingAnimalFarmCertificateEntity::getExpirationDate)
                .set(BreedingAnimalFarmCertificatePreviewDTO::getExcerptFileId)
                .with(certificate -> certificate.getRegistryFile().getId())
                .omitOthers()
                .mapper();
    }
}
