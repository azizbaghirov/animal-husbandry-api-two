package az.eagro.animalhusbandry.api;

import az.eagro.animalhusbandry.api.service.model.error.GenericError;
import az.eagro.animalhusbandry.business.AccessForbiddenException;
import az.eagro.animalhusbandry.business.BusinessException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ResponseEntity<GenericError> accessDeniedException(AccessForbiddenException exc, HttpServletRequest request) {
        GenericError error = GenericError.builder().errorMessage(exc.getMessage()).build();
        log.error(ExceptionUtils.getStackTrace(exc));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<GenericError> businessException(BusinessException exc, HttpServletRequest request) {
        GenericError error = GenericError.builder().errorMessage(exc.getMessage()).build();
        log.error(ExceptionUtils.getStackTrace(exc));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest webRequest) {
        ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation Error",
                ((ServletWebRequest) webRequest).getRequest().getRequestURI());
        Map<String, String> err = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> err.put(error.getField(), error.getDefaultMessage()));
        validationError.setValidationErrors(err);
        log.error(ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}