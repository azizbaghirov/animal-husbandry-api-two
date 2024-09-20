package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.DataType;
import az.eagro.animalhusbandry.model.FieldType;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldSummaryDTO {

    private Integer id;
    private String label;
    private String name;
    private String description;
    private Integer fieldOrder;
    private boolean mandatory;
    private DataType dataType;
    private FieldType fieldType;
    private List<FieldValidationRuleDTO> fieldValidationRules;
    private MeasurementUnitDTO measurementUnit;

}
