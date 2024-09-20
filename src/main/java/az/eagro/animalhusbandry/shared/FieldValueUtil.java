package az.eagro.animalhusbandry.shared;

import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.model.DataType;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import java.math.BigDecimal;

public class FieldValueUtil {

    public static Object getValueByType(FieldValueEntity fieldValue, DataType dataType) {
        if (fieldValue != null) {
            switch (dataType) {
                case INTEGER -> {
                    if ((fieldValue.getIntValue() != null) && (fieldValue.getIntValue() < 0)) {
                        throw new BusinessException(" '" + fieldValue.getField().getName() + "' mənfi ədəd ola bilməz. ");
                    }
                    return fieldValue.getIntValue();
                }
                case DECIMAL -> {
                    if ((fieldValue.getDecimalValue() != null) && (fieldValue.getDecimalValue().compareTo(BigDecimal.ZERO) < 0)) {
                        throw new BusinessException(" '" + fieldValue.getField().getName() + "' mənfi ədəd ola bilməz. ");
                    }
                    return fieldValue.getDecimalValue();
                }
                case TEXT -> {
                    return fieldValue.getTextValue();
                }
                case BOOLEAN -> {
                    return fieldValue.getBooleanValue();
                }
                default -> {
                }
            }
        }
        return null;
    }

    public static void checkValueByType(FieldValueEntity fieldValue, DataType dataType) {
        if (fieldValue == null) {
            return;
        }

        switch (dataType) {
            case INTEGER:
                checkIntegerValue(fieldValue);
                break;
            case DECIMAL:
                checkDecimalValue(fieldValue);
                break;
            case TEXT:
                if (fieldValue.getTextValue() == null) {
                    throw new BusinessException("textValue boş ola bilməz.");
                }
                break;
            case BOOLEAN:
                if (fieldValue.getBooleanValue() == null) {
                    throw new BusinessException("booleanValue boş ola bilməz.");
                }
                break;
            default:
                break;
        }
    }

    private static void checkIntegerValue(FieldValueEntity fieldValue) {
        if (fieldValue.getIntValue() == null) {
            throw new BusinessException("intValue boş ola bilməz.");
        } else if (fieldValue.getIntValue() < 0) {
            throw new BusinessException("'" + fieldValue.getField().getName() + "' mənfi ədəd ola bilməz.");
        }
    }

    private static void checkDecimalValue(FieldValueEntity fieldValue) {
        if (fieldValue.getDecimalValue() == null) {
            throw new BusinessException("decimalValue boş ola bilməz.");
        } else if (fieldValue.getDecimalValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("'" + fieldValue.getField().getName() + "' mənfi ədəd ola bilməz.");
        }
    }
}
