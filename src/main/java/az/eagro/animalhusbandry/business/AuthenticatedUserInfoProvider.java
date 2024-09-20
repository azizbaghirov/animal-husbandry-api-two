package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.AuthenticatedUserContext;
import az.eagro.animalhusbandry.api.service.model.LegalPersonDTO;
import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import az.eagro.animalhusbandry.model.UserRole;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuthenticatedUserInfoProvider {

    public AuthenticatedUserContext getAuthenticatedUserContext() {
        return (AuthenticatedUserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UUID getUserId() {
        return getAuthenticatedUserContext().getUserId();
    }

    public String getUserPin() {
        return getAuthenticatedUserContext().getPin();
    }

    public String getUserName() {
        return getAuthenticatedUserContext().getName();
    }

    public String getUserSurname() {
        return getAuthenticatedUserContext().getSurname();
    }

    public String getUserPatronymic() {
        return getAuthenticatedUserContext().getPatronymic();
    }

    public LegalPersonDTO getLegalPerson() {
        return getAuthenticatedUserContext().getLegalPerson();
    }

    public boolean isLegal() {
        return (getLegalPerson() != null) && getLegalPerson().isLegal();
    }

    public List<RegionDTO> getRegions() {
        return getAuthenticatedUserContext().getRegions();
    }

    public List<Integer> getRegionIds() {
        return getAuthenticatedUserContext().getRegions().stream().map(RegionDTO::getId).collect(Collectors.toList());
    }

    public boolean isOperator() {
        return (isInspector() || isCommissionChairman() || isCommissionMember() || isSupervisor());
    }

    public boolean isInspector() {
        return getAuthenticatedUserContext().getRoles().stream()
                .anyMatch(role -> role.getRoleLabel().equals(UserRole.INSPECTOR.name()));

    }

    public boolean isCommissionMember() {
        return getAuthenticatedUserContext().getRoles().stream()
                .anyMatch(role -> role.getRoleLabel().equals(UserRole.COMMISSION_MEMBER.name()));

    }

    public boolean isSupervisor() {
        return getAuthenticatedUserContext().getRoles().stream()
                .anyMatch(role -> role.getRoleLabel().equals(UserRole.SUPERVISOR.name()));

    }

    public boolean isCommissionChairman() {
        return getAuthenticatedUserContext().getRoles().stream()
                .anyMatch(role -> role.getRoleLabel().equals(UserRole.COMMISSION_CHAIRMAN.name()));

    }

    public boolean isCommissionChairmanOrMemberOrSupervisor() {
        return isCommissionChairman() || isCommissionMember() || isSupervisor();

    }
}