package az.eagro.animalhusbandry.api.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperatorsJudgementStateDTO {

    private boolean existsInMembers;
    private boolean existsJudgement;

}
