package az.eagro.animalhusbandry.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ValidationError {

    private final int status;
    private final String message;
    private final String path;
    private long timestamp = new Date().getTime();
    private Map<String, String> validationErrors = new HashMap<>();

}