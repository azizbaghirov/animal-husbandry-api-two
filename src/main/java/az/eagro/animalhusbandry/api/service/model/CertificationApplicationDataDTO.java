package az.eagro.animalhusbandry.api.service.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationApplicationDataDTO {

    private Integer id;
    private Integer farmTypeId;
    private Integer animalTypeId;
    private ApplicationInitialMonitoringDTO application;
    private List<FieldValueDTO> fieldValues;

}
