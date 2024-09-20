package az.eagro.animalhusbandry;

import az.eagro.animalhusbandry.api.service.model.PersonDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedToken {

    public AuthenticationToken buildAuthenticationToken(PersonDTO user) {
        return new AuthenticationToken(buildAuthenticatedUserContext(user), buildGrantedAuthorities(user));
    }

    private AuthenticatedUserContext buildAuthenticatedUserContext(PersonDTO personDTO) {

        return AuthenticatedUserContext.builder()
                .userId(personDTO.getUserId())
                .pin(personDTO.getPin())
                .roles(personDTO.getRoles().stream().map(regionBasedRole -> {
                    regionBasedRole.setRoleLabel(regionBasedRole.getRoleLabel().toUpperCase());
                    return regionBasedRole;
                }).toList())
                .name(personDTO.getName())
                .surname(personDTO.getSurname())
                .patronymic(personDTO.getPatronymic())
                .legalPerson(personDTO.getLegalPerson())
                .regions(personDTO.getRegions())
                .build();
    }

    private Set<SimpleGrantedAuthority> buildGrantedAuthorities(PersonDTO personDTO) {
        return personDTO.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleLabel().toUpperCase()))
                .collect(Collectors.toSet());
    }
}
