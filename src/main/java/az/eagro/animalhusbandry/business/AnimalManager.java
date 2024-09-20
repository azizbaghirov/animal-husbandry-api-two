package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.feign.EagroVsmAnimalFeignClient;
import az.eagro.animalhusbandry.feign.model.AnimalStatusDTO;
import az.eagro.animalhusbandry.feign.model.AnimalSummaryDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AnimalManager {

    public static final int BATCH_SIZE = 1000;
    private final EagroVsmAnimalFeignClient animalFeignClient;
    private final AuthenticatedUserInfoProvider auth;
    private final BreedingAnimalFarmManager farmManager;
    private final FarmerAccountManager farmer;
    private final CertificationApplicationAccessValidator applicationAccessValidator;
    private final AnimalValidation animalValidation;
    private final CertificationApplicationManager certificationApplicationManager;

    public Page<AnimalSummaryDTO> getAnimals(String token, Integer farmId, String nicknameOrTagId, Pageable pageable) {

        farmer.findFarmerAccount();

        BreedingAnimalFarmEntity farm = farmManager.getByIdAndFarmer(farmId);

        String taxNumber = auth.isLegal() ? auth.getLegalPerson().getTaxPayerIdentificationNumber() : null;

        if (farm.getAnimalType().getFarmType().getLabel() == BreedingAnimalFarmType.APICULTURE) {
            return new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 1), 0);
        }

        return animalFeignClient.getAnimals(token, auth.getUserPin(), taxNumber, farm.getFarmType().getAnimalCategory(),
                farm.getAnimalType().getAnimalType(), farm.getAdministrativeArea().getId(), null, nicknameOrTagId, pageable);
    }

    public Set<Integer> getAnimalIds(
            String token, Integer farmId, String nicknameOrTagId, boolean selectedAll, Set<Integer> ignoredIds, Set<Integer> markedIds) {

        Set<Integer> result = getAnimals(token, farmId, nicknameOrTagId, PageRequest.of(0, Integer.MAX_VALUE)).getContent()
                .stream()
                .map(AnimalSummaryDTO::getId)
                .collect(Collectors.toSet());

        if (selectedAll) {
            result.removeAll(ignoredIds);
            return result;
        }

        animalValidation.checkBxmAnimals(result, markedIds);
        return markedIds;
    }

    public Page<AnimalStatusDTO> getAnimalsByApplicationId(String token, Integer applicationId, Pageable pageable) {
        applicationAccessValidator.validateAccess(applicationId);
        List<Integer> allAnimalIds = certificationApplicationManager.getAnimalIdsByApplicationId(applicationId).getAnimalIds().stream().toList();
        List<AnimalStatusDTO> animals = new ArrayList<>();

        List<List<Integer>> partitionIdList = partitionList(allAnimalIds);

        for (List<Integer> batchIds : partitionIdList) {
            List<AnimalStatusDTO> batchResult = animalFeignClient.getAnimalsByIds(token, batchIds);
            animals.addAll(batchResult);
        }
        int totalElement = animals.size();

        int start = pageable.getPageSize() * pageable.getPageNumber();
        int end = Math.min((start + pageable.getPageSize()), totalElement);
        List<AnimalStatusDTO> paginatedList = animals.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, totalElement);
    }

    private <T> List<List<T>> partitionList(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(it -> list.indexOf(it) / BATCH_SIZE))
                .values()
                .stream()
                .toList();
    }

}
