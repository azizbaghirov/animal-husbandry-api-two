package az.eagro.animalhusbandry.shared.asanlogin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SsoResponse {

    private String timestamp;
    private int status;
    private String description;
    private String transaction;
    private SsoError exception;

    public boolean isAuthorized() {
        return status == 200;
    }

    public boolean isUnauthorized() {
        return status == 401;
    }

    public boolean isForbidden() {
        return status == 403;
    }

    @Setter
    @Getter
    @ToString
    public static class SsoError {
        private Integer code;
        private String message;
    }
}
