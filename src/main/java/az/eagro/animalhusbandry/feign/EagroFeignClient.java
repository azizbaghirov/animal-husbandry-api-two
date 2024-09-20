package az.eagro.animalhusbandry.feign;

import az.eagro.animalhusbandry.feign.configuration.FeignClientConfiguration;
import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import java.util.Map;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "eagro-animal-husbandry-integration-api", url = "${eagro.api.url}", configuration = FeignClientConfiguration.class)
public interface EagroFeignClient {

    @GetMapping("/eagro-animal-husbandry-integration-api/api/v1/fields")
    Set<FieldDocumentDTO> getActs(@RequestParam Integer administrativeAreaId,
            @RequestHeader("personalIdentificationNumber") String personalIdentificationNumber,
            @RequestHeader("taxpayerIdentificationNumber") String taxpayerIdentificationNumber);

    @PostMapping(value = "/agroculture/api/files/s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, String> fileUpload(@RequestPart(value = "multipartFile")MultipartFile multipartFile);

}
