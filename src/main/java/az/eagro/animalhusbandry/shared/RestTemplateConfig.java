package az.eagro.animalhusbandry.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class RestTemplateConfig {

    @Value("${asanlogin.server-url}")
    private String asanUrl;

    @Value("${ektis.base-url}")
    private String ektisBaseUrl;

    @Value("${ektis.username}")
    private String ektisUsername;

    @Value("${ektis.password}")
    private String ektisPassword;

    @Bean("asanRestTemplate")
    @Primary
    public RestTemplate asanRestTemplate(RestTemplateBuilder builder) {
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(asanUrl)).build();
    }

    @Bean("ektisRestTemplate")
    public RestTemplate ektisRestTemplate(RestTemplateBuilder builder) {
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(ektisBaseUrl))
                .interceptors(new BasicAuthenticationInterceptor(ektisUsername, ektisPassword))
                .build();
    }
    
}
