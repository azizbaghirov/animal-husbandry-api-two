package az.eagro.animalhusbandry;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@SecurityScheme(
        name = "SID",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.APIKEY,
        bearerFormat = "JWT",
        scheme = "bearer")
@OpenAPIDefinition(
        info = @Info(title = "Animal Husbandry API", version = "1.0.0"),
        servers = {
                @Server(url = "http://localhost:9000/animal-husbandry", description = "Local"),
                @Server(url = "https://animal-husbandry-api-staging.eagro.az/animal-husbandry-api", description = "Deployment")
        })
@EnableJpaAuditing
@EnableFeignClients
@EnableEnversRepositories(basePackages = {"az.eagro.animalhusbandry.repository"})
public class AnimalHusbandryApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(AnimalHusbandryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AnimalHusbandryApplication.class)
                .properties("spring.config.name = animal-husbandry");
    }

}
