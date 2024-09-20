package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.DataType;
import az.eagro.animalhusbandry.model.FieldEntity;
import az.eagro.animalhusbandry.model.FieldType;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import az.eagro.animalhusbandry.shared.FieldValueUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldValidation {

    private final FieldManager fieldManager;
    private final FieldValueCreationValidator fieldValueCreationValidator;

    public void ensureAllFieldsSent(Integer farmId, List<FieldValueEntity> fieldValues) {

        List<FieldEntity> fields = fieldManager.getFieldsByFarmId(farmId);

        Set<Integer> configFieldIds = fields.stream().map(FieldEntity::getId).collect(Collectors.toSet());
        Set<Integer> declaredFields = fieldValues.stream().map(fieldValueEntity -> fieldValueEntity.getField().getId()).collect(Collectors.toSet());

        if (!configFieldIds.equals(declaredFields)) {
            throw new BusinessException("Daxil edilən sahələr (Field) konfuqurasiyaya uyğun deyil.");
        }

        checkMandatoryFields(declaredFields, fields, FieldType.ASSET, "Vacib sayılan norma(lar) seçilməyib");
        checkMandatoryFields(declaredFields, fields, FieldType.FARM_INFO, "Vacib sayılan sahə(lər) seçilməyib");

        validateFieldRules(fieldValues, fields);

    }

    private void checkMandatoryFields(Set<Integer> declaredFields, List<FieldEntity> fields, FieldType fieldType, String message) {
        Set<Integer> mandatoryFieldIds = fields.stream()
                .filter(fieldEntity -> fieldEntity.getFieldType() == fieldType && fieldEntity.isMandatory())
                .map(FieldEntity::getId)
                .collect(Collectors.toSet());

        if (!declaredFields.containsAll(mandatoryFieldIds)) {
            throw new BusinessException(message);
        }
    }

    private void validateFieldRules(List<FieldValueEntity> fieldValues, List<FieldEntity> fields) {

        Map<String, Object> params = new HashMap<>();
        List<FieldValueEntity> fieldValueList = new ArrayList<>();

        fields.stream()
                .filter(field -> field.getFieldType() == FieldType.FARM_INFO)
                .forEach(fieldEntity -> {
                    var fieldValue = fieldValues.stream()
                            .filter(fv -> fv.getField().getId().equals(fieldEntity.getId()))
                            .findFirst().orElse(null);
                    fieldValue.setField(fieldEntity);
                    fieldValueList.add(fieldValue);

                    params.put(fieldEntity.getLabel(), FieldValueUtil.getValueByType(fieldValue, fieldEntity.getDataType()));
                });
        fieldValueList.forEach(fieldValue -> {
            fieldValueCreationValidator.ensureFieldValueIsValid(fieldValue, params);
        });
    }

}
