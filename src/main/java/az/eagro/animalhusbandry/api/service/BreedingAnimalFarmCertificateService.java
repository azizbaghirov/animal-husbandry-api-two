package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmCertificatePreviewDTO;
import az.eagro.animalhusbandry.business.certification.BreedingAnimalFarmCertificateManager;
import az.eagro.animalhusbandry.model.certification.BreedingAnimalFarmCertificateEntity;
import com.remondis.remap.Mapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BreedingAnimalFarmCertificateService {

    private final BreedingAnimalFarmCertificateManager breedingAnimalFarmCertificateManager;
    private final Mapper<BreedingAnimalFarmCertificateEntity, BreedingAnimalFarmCertificatePreviewDTO> breedingAnimalFarmCertificatePreviewMapper;

    public Optional<BreedingAnimalFarmCertificatePreviewDTO> findPreviewByCertificationApplicationId(Integer certificationApplicationId) {
        return breedingAnimalFarmCertificateManager.findByIdCertificationApplication(certificationApplicationId)
                .map(breedingAnimalFarmCertificatePreviewMapper::map);
    }
}
