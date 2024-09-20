package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.certification.CertificationCommissionMemberDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.FinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.FinalMonitoringJudgementSummaryDTO;
import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.business.certification.CertificationCommissionMembershipManager;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringJudgementManager;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMemberEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementSummaryService {

    private final FinalMonitoringJudgementManager finalMonitoringJudgementManager;
    private final CertificationCommissionMembershipManager certificationCommissionMembershipManager;
    private final Mapper<CertificationCommissionMemberEntity, CertificationCommissionMemberDTO>
            certificationCommissionMemberModelDTOMapper;
    private final Mapper<FinalMonitoringJudgementEntity, FinalMonitoringJudgementDTO> finalMonitoringJudgementModelDTOMapper;
    private final AuthenticatedUserInfoProvider authInfoProvider;

    public FinalMonitoringJudgementSummaryDTO getSummaryByCertificationApplicationId(Integer applicationId) {

        List<CertificationCommissionMemberEntity> members = certificationCommissionMembershipManager.getCurrentCertificationCommissionMembers();
        List<FinalMonitoringJudgementEntity> judgements = finalMonitoringJudgementManager.findAllByCertificationApplication(applicationId);

        List<CertificationCommissionMemberDTO> memberDTOs = certificationCommissionMemberModelDTOMapper.map(members);
        List<FinalMonitoringJudgementDTO> judgementDTOs = finalMonitoringJudgementModelDTOMapper.map(judgements)
                .stream().peek(dto -> {
                    if (dto.getAuthor().getId().equals(authInfoProvider.getUserId())) {
                        dto.setAuthUserIsOwner(true);
                    }
                })
                .collect(Collectors.toList());

        return FinalMonitoringJudgementSummaryDTO.builder()
                .certificationCommissionMembers(memberDTOs)
                .finalMonitoringJudgements(judgementDTOs)
                .build();
    }
}
