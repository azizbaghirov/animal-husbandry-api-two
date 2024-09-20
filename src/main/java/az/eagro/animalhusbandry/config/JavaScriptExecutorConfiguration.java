package az.eagro.animalhusbandry.config;

import az.eagro.animalhusbandry.shared.JavaScriptExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class JavaScriptExecutorConfiguration {

    @Bean
    public JavaScriptExecutor executor(final ObjectMapper objectMapper) {
        return new JavaScriptExecutor(objectMapper);
    }

}
