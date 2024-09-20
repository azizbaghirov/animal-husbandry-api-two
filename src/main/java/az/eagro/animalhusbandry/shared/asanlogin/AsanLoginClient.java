package az.eagro.animalhusbandry.shared.asanlogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class AsanLoginClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AsanLoginClient(@Qualifier("asanRestTemplate") RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows({IOException.class})
    public SsoResponse authorizeUser(HttpServletRequest httpServletRequest) {
        ResponseEntity<SsoResponse> ssoResponse;

        try {
            ssoResponse = restTemplate.exchange("", HttpMethod.GET,
                    buildHttpEntity(httpServletRequest), SsoResponse.class);

            log.info("ASAN Login Response: {}", ssoResponse);
        } catch (HttpClientErrorException ex) {
            log.error("Error occurred while checking token", ex);
            byte[] responseBodyAsByteArray = ex.getResponseBodyAsByteArray();
            return objectMapper.readValue(responseBodyAsByteArray, SsoResponse.class);
        }

        return ssoResponse.getBody();
    }

    private HttpEntity<String> buildHttpEntity(HttpServletRequest httpServletRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = httpServletRequest.getHeader("SID");
        if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
            token = token.replaceFirst("Bearer ", "");
        }

        httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
        httpHeaders.set("ip", httpServletRequest.getHeader("ip"));
        httpHeaders.set("AppLabel", httpServletRequest.getHeader("AppLabel"));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>("params", httpHeaders);
    }
}
