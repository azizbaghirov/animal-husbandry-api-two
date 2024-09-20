package az.eagro.animalhusbandry.business;


import az.eagro.animalhusbandry.model.DiscoveredDataEntity;
import az.eagro.animalhusbandry.model.FieldValueEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDataEntity;
import az.eagro.animalhusbandry.shared.FieldValueUtil;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDataValidation {

    private final CertificationApplicationDataManager applicationDataManager;

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void checkCertificationApplicationData(InitialMonitoringDataEntity initialMonitoringData) {

        Integer applicationId = initialMonitoringData.getMonitoringDecision().getApplication().getId();

        List<FieldValueEntity> fieldValues = applicationDataManager.getByApplicationIdAndIsRegistered(applicationId).getFieldValues();

        Set<Integer> fieldValueIds = fieldValues.stream().map(FieldValueEntity::getId).collect(Collectors.toSet());
        Set<Integer> declaredFieldValueIds = initialMonitoringData.getDiscoveredData().stream()

                .map(discoveredDataEntity -> discoveredDataEntity.getDeclaredValue().getId()).collect(Collectors.toSet());

        if (!fieldValueIds.equals(declaredFieldValueIds)) {
            throw new BusinessException("Daxil edilən sahə məlumatları (FieldValue) siyahısı konfiqurasiyaya uyğun deyil. ");
        }
        ensureFieldValuesIsCorrect(initialMonitoringData, fieldValues);

    }

    private void ensureFieldValuesIsCorrect(InitialMonitoringDataEntity initialMonitoringData, List<FieldValueEntity> fieldValues) {

        List<DiscoveredDataEntity> list = initialMonitoringData.getDiscoveredData()
                .stream().filter(InitialMonitoringDataValidation::checkValueEqualsAndDiscoveredValueData).toList();

        if (list.isEmpty()) {
            return;
        }

        list.forEach(discoveredData -> {
            var fieldValue = fieldValues.stream()
                    .filter(value -> {
                        if (Objects.equals(value.getId(), discoveredData.getDeclaredValue().getId())) {
                            if ((!value.getField().getId().equals(discoveredData.getDiscoveredValue().getField().getId()))) {
                                throw new BusinessException("DeclaredValue və Field uyğun deyil.");
                            }
                            return true;
                        }
                        return false;
                    }).findFirst().get();
            if (!ObjectUtils.isEmpty(fieldValue)) {
                FieldValueUtil.checkValueByType(discoveredData.getDiscoveredValue(), fieldValue.getField().getDataType());
            }
        });
    }

    private static boolean checkValueEqualsAndDiscoveredValueData(DiscoveredDataEntity discoveredData) {
        if (!discoveredData.isValuesEqual() && !ObjectUtils.isEmpty(discoveredData.getDiscoveredValue())) {
            return true;
        } else if (discoveredData.isValuesEqual() && !ObjectUtils.isEmpty(discoveredData.getDiscoveredValue())) {
            throw new BusinessException("If valuesEqual is true, then discoveredValue cannot be present.");
        } else if (!discoveredData.isValuesEqual() && ObjectUtils.isEmpty(discoveredData.getDiscoveredValue())) {
            throw new BusinessException("If discoveredValue is null, then valuesEqual must be true.");
        }
        return false;
    }


}
