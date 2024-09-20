package az.eagro.animalhusbandry.feign;

import az.eagro.animalhusbandry.business.PageModule;
import az.eagro.animalhusbandry.feign.model.AnimalStatusDTO;
import az.eagro.animalhusbandry.feign.model.AnimalSummaryDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "vsm-animal-api", value = "vsm-animal-api", url = "${eagro.animal.api.url}")
public interface EagroVsmAnimalFeignClient {

    @GetMapping("/ektis/animal/husbandry-animal")
    PageModule<AnimalSummaryDTO> getAnimals(
            @RequestHeader("SID") String token,
            @RequestParam(value = "personalIdentificationNumber") String personalIdentificationNumber,
            @RequestParam(value = "taxpayerIdentificationNumber", required = false) String taxpayerIdentificationNumber,
            @RequestParam("animalCategory") Integer animalCategory,
            @RequestParam("animalType") Integer animalType,
            @RequestParam("areaId") Integer areaId,
            @RequestParam(value = "animalProductivityDirection", required = false) Integer animalProductivityDirection,
            @RequestParam(value = "nicknameOrTagId", required = false) String nicknameOrTagId,
            Pageable pageable);

    @GetMapping("/ektis/animal/husbandry-animals-by-ids")
    List<AnimalStatusDTO> getAnimalsByIds(
            @RequestHeader("SID") String token,
            @RequestParam(name = "animalIds") List<Integer> animalIds);
}