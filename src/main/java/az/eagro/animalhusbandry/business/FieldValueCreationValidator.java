package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.FieldValueEntity;
import az.eagro.animalhusbandry.shared.FieldValidationRuleUtils;
import az.eagro.animalhusbandry.shared.JavaScriptExecutor;
import java.math.BigDecimal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldValueCreationValidator {

    private final JavaScriptExecutor jsExecutor;

    public void ensureFieldValueIsValid(FieldValueEntity value, Map<String, Object> params) {

        var rules = value.getField().getFieldValidationRules();
        var dataType = value.getField().getDataType();

        if (!rules.isEmpty()) {
            rules.parallelStream().forEach(rule -> {

                if (rule.getExpression() != null) {

                    boolean result = Boolean.parseBoolean(jsExecutor.executeAndGetResult(rule.getExpression(), params, String.class));
                    if (!result) {
                        throw new BusinessException(rule.getMessage());
                    }

                } else {

                    switch (dataType) {
                        case INTEGER -> FieldValidationRuleUtils.validateIntegerValue(
                                value.getIntValue(), rule.getMaxInteger(), rule.getMinInteger(),
                                value.getField().getLabel() + " " + rule.getMessage());

                        case DECIMAL -> FieldValidationRuleUtils.validateDecimalValue(
                                value.getDecimalValue(), rule.getMaxDecimal(), rule.getMinDecimal(), rule.getScale(),
                                value.getField().getLabel() + " " + rule.getMessage());

                        case TEXT -> FieldValidationRuleUtils.validateTextValue(
                                value.getTextValue(), rule.getMaxLength(), rule.getMinLength(),
                                value.getField().getLabel() + " " + rule.getMessage());
                        default -> {
                        }
                    }
                }

            });
        }
    }

}
