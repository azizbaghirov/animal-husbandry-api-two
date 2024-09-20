package az.eagro.animalhusbandry.shared.ektis;

import az.eagro.animalhusbandry.api.service.model.AdministrativeAreaDTO;
import az.eagro.animalhusbandry.api.service.model.RegionBasedRole;
import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class EktisClient {

    private final RestTemplate restTemplate;

    public EktisClient(@Qualifier("ektisRestTemplate") RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
    }

    public List<AdministrativeAreaDTO> getAdministrativeAreas(Integer regionId) {
        try {
            var responseType = new ParameterizedTypeReference<List<AdministrativeAreaDTO>>() {};
            var responseEntity = restTemplate
                    .exchange("/common/administrative-areas?regionId=" + regionId, HttpMethod.GET, null, responseType);
            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Entity not found", ex);
        }
    }

    public List<RegionDTO> getRegions() {
        try {
            var responseType = new ParameterizedTypeReference<List<RegionDTO>>() {};
            var responseEntity = restTemplate.exchange("/common/regions", HttpMethod.GET, null, responseType);
            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Entity not found", ex);
        }
    }

    //@SneakyThrows({IOException.class})
    public List<RegionBasedRole> getRoleBasedUserRegionAccess(String pin) {
        try {
            var responseType = new ParameterizedTypeReference<List<RegionBasedRole>>() {};
            HttpHeaders headers = new HttpHeaders();
            headers.set("personalIdentificationNumber", pin);
            HttpEntity requestEntity = new HttpEntity<>("", headers);
            var responseEntity = restTemplate.exchange("/management/role-based-user-region-access", HttpMethod.GET, requestEntity, responseType);
            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Entity not found", ex);
        }
    }
}
