package az.eagro.animalhusbandry.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("SID", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .name("SID")
                                .in(SecurityScheme.In.HEADER)))
                .addSecurityItem(new SecurityRequirement().addList("SID"))
                .info(new Info()
                        .title("Animal Husbandry")
                        .description("Animal Husbandry API")
                        .version("1.0.0")
                        .contact(new Contact().name("Digital Umbrella").email("contact@digirella.az"))
                );
    }



}
