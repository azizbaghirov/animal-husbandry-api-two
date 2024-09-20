package az.eagro.animalhusbandry.shared;

import az.eagro.animalhusbandry.business.BusinessException;
import java.math.BigDecimal;

public class FieldValidationRuleUtils {

    public static void validateIntegerValue(Integer value, Integer max, Integer min, String message) {
        if (value == null || (min != null && value < min) || (max != null && value > max)) {
            throw new BusinessException(message + " [" + min + "-" + max + "] ");
        }
    }

    public static void validateDecimalValue(BigDecimal value, BigDecimal max, BigDecimal min, Integer scale, String message) {
        if (value == null
                || (min != null && value.compareTo(min) < 0)
                || (max != null && value.compareTo(max) > 0)
                || (scale != null && value.scale() > scale)) {
            throw new BusinessException(message + " [" + min + "-" + max + "], scale: " + scale);
        }
    }

    public static void validateTextValue(String textValue, Integer maxLength, Integer minLength, String message) {
        if (textValue == null
                || (minLength != null && textValue.length() < minLength)
                || (maxLength != null && textValue.length() > maxLength)) {
            throw new BusinessException(message);
        }
    }

}
