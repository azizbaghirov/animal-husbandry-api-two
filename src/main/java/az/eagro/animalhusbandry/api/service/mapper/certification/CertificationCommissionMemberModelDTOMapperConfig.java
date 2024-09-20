package az.eagro.animalhusbandry.api.service.mapper.certification;

import az.eagro.animalhusbandry.api.service.model.certification.CertificationCommissionMemberDTO;
import az.eagro.animalhusbandry.model.GrantedAuthorityEntity;
import az.eagro.animalhusbandry.model.UserRole;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMemberEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CertificationCommissionMemberModelDTOMapperConfig {

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<CertificationCommissionMemberEntity, CertificationCommissionMemberDTO> certificationCommissionMemberModelDTOMapper() {
        return Mapping.from(CertificationCommissionMemberEntity.class)
                .to(CertificationCommissionMemberDTO.class)
                .omitInSource(CertificationCommissionMemberEntity::getMembership)
                .omitInSource(CertificationCommissionMemberEntity::getOperator)
                .set(CertificationCommissionMemberDTO::getName).with(member -> member.getOperator().getName())
                .set(CertificationCommissionMemberDTO::getSurname).with(member -> member.getOperator().getSurname())
                .set(CertificationCommissionMemberDTO::getPatronymic).with(member -> member.getOperator().getPatronymic())
                .set(CertificationCommissionMemberDTO::getAuthorId).with(member -> member.getOperator().getId())
                .set(CertificationCommissionMemberDTO::getRole).with(member -> member.getOperator().getGrantedAuthorities()
                        .stream().map(GrantedAuthorityEntity::getRole).collect(Collectors.toSet()))
                .mapper();
    }
}
