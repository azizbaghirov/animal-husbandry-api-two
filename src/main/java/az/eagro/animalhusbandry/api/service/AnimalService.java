package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.PageResponse;
import az.eagro.animalhusbandry.business.AnimalManager;
import az.eagro.animalhusbandry.feign.model.AnimalStatusDTO;
import az.eagro.animalhusbandry.feign.model.AnimalSummaryDTO;
import az.eagro.animalhusbandry.feign.model.FindAnimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalService {

    private final AnimalManager animalManager;

    public PageResponse<AnimalSummaryDTO> getAnimals(String token, Integer farmId, FindAnimalDTO findAnimal) {
        Pageable pageable = PageRequest.of(findAnimal.getPage(), findAnimal.getSize());
        Page<AnimalSummaryDTO> animals = animalManager.getAnimals(token, farmId, findAnimal.getNicknameOrTagId(), pageable);
        return new PageResponse<>(animals.getContent(), animals.getTotalPages(), animals.getTotalElements());
    }

    public PageResponse<AnimalStatusDTO> getAnimalsByApplicationId(
            String token, Integer applicationId, az.eagro.animalhusbandry.api.PageRequest pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
        Page<AnimalStatusDTO> result = animalManager.getAnimalsByApplicationId(token, applicationId, pageable);
        return new PageResponse<>(result.getContent(), result.getTotalPages(), result.getTotalElements());
    }
}
