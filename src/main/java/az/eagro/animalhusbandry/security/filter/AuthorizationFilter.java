package az.eagro.animalhusbandry.security.filter;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import az.eagro.animalhusbandry.AuthenticatedToken;
import az.eagro.animalhusbandry.api.service.OperatorService;
import az.eagro.animalhusbandry.api.service.model.PersonDTO;
import az.eagro.animalhusbandry.api.service.model.RegionBasedRole;
import az.eagro.animalhusbandry.shared.TokenParser;
import az.eagro.animalhusbandry.shared.asanlogin.AsanLoginClient;
import az.eagro.animalhusbandry.shared.asanlogin.SsoResponse;
import az.eagro.animalhusbandry.shared.ektis.EktisClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AsanLoginClient asanLoginClient;
    private final AuthenticatedToken authenticatedToken;
    private final ObjectMapper objectMapper;
    private final EktisClient ektisClient;
    private final OperatorService operatorService;

    @SneakyThrows(JSONException.class)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Authentication filter starts execution");
        SecurityContextHolder.clearContext();
        String token = request.getHeader("SID");

        if (StringUtils.isNotEmpty(token)) {
            log.info("Authorize User");
            SsoResponse ssoResponse = asanLoginClient.authorizeUser(request);

            if (ssoResponse.isUnauthorized()) {
                buildUnauthorizedError(ssoResponse, response);
                return;
            }

            if (ssoResponse.isForbidden()) {
                log.error("User is forbidden");
                buildErrorResponse(response, "İstifadəçinin icazəsi yoxdur müəyyən resurslara", FORBIDDEN);
                return;
            }

            PersonDTO personDTO = TokenParser.parse(token);
            var operatorDTO = operatorService.getOperatorById(personDTO.getPin());

            if (ObjectUtils.isEmpty(operatorDTO)) {
                personDTO.setRoles(ektisClient.getRoleBasedUserRegionAccess(personDTO.getPin()));
            } else {
                personDTO.setRoles(operatorDTO.getGrantedAuthorities().stream()
                        .map(grantedAuthorityDTO -> {
                            return RegionBasedRole.builder().roleLabel(grantedAuthorityDTO.getRole().name()).build();
                        })
                        .collect(Collectors.toList()));
                personDTO.setRegions(operatorDTO.getRegions());
            }


            log.info("User token set to SecurityContextHolder");
            AbstractAuthenticationToken authenticationToken = authenticatedToken.buildAuthenticationToken(personDTO);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        log.info("Authentication filter ends execution");
        filterChain.doFilter(request, response);
    }

    private void buildUnauthorizedError(SsoResponse ssoResponse, HttpServletResponse response) throws IOException {
        Integer errorCode = ssoResponse.getException().getCode();
        log.error("User is unauthorized, error code [{}]", errorCode);

        if (errorCode.equals(1301)) {
            buildErrorResponse(response, "Token düzgün deyil", UNAUTHORIZED);
        }

        if (errorCode.equals(1701)) {
            buildErrorResponse(response, "Token tapılmadı", UNAUTHORIZED);
        }

        if (errorCode.equals(1702)) {
            buildErrorResponse(response, "Sizin sessiya vaxtınız bitmişdir", UNAUTHORIZED);
        }
    }

    private void buildErrorResponse(HttpServletResponse response, String message, HttpStatus httpStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        response.getOutputStream().write(objectMapper.writer().writeValueAsBytes(message));
        response.getOutputStream().flush();
    }
}
