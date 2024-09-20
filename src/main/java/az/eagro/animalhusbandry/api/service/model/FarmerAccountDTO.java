package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.FarmerAccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmerAccountDTO {

    private FarmerAccountType farmerAccountType;
    private LegalPersonDTO legalPerson;
    private PhysicalPersonDTO physicalPerson;

}
