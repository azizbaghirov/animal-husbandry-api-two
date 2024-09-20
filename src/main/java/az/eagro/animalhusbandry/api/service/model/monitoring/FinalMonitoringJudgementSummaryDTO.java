package az.eagro.animalhusbandry.api.service.model.monitoring;

import az.eagro.animalhusbandry.api.service.model.certification.CertificationCommissionMemberDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalMonitoringJudgementSummaryDTO {

    private List<CertificationCommissionMemberDTO> certificationCommissionMembers;
    private List<FinalMonitoringJudgementDTO> finalMonitoringJudgements;

}
