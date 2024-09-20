package az.eagro.animalhusbandry;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final AuthenticatedUserContext authenticatedUserContext;

    public AuthenticationToken(AuthenticatedUserContext authenticatedUserContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
        this.authenticatedUserContext = authenticatedUserContext;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public AuthenticatedUserContext getPrincipal() {
        return authenticatedUserContext;
    }
}
