package sep490.idp.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import sep490.common.api.exceptions.TechnicalException;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.security.UserAuthenticationToken;
import sep490.idp.security.UserContextData;

import java.util.List;
import java.util.Optional;

@Slf4j
public final class SecurityUtils {

    private static final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private SecurityUtils() {
        // Utility class. No instantiation allowed.
    }

    public static String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new TechnicalException("No authentication present in context");
        }
        if (authentication instanceof JwtAuthenticationToken jwt) {
            return jwt.getName();
        }
        return getUserContextData().orElseThrow(() -> new TechnicalException("Authentication principal not exists")).getUsername();
    }
    
    public static Optional<UserContextData> getUserContextData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.warn("No authentication present in context");
            return Optional.empty();
        }
        if (authentication instanceof JwtAuthenticationToken) {
            log.warn("Cannot retrieve user context data from JWT authentication token. Principal is not of type UserContextData");
            throw new TechnicalException("Cannot retrieve user context data from JWT authentication token");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserContextData currentUser) {
            return Optional.of(currentUser);
        }
        return Optional.empty();
    }

    public static List<BuildingPermissionEntity> getPermissions() {
        Optional<UserContextData> currentUser = getUserContextData();
        return currentUser.map(UserContextData::getPermissions).orElse(List.of());
    }

    public static void storeAuthenticationToContext(UserAuthenticationToken authenticationToken,
                                                    HttpServletRequest request, HttpServletResponse response) {
        if (authenticationToken == null || request == null || response == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        try {
            SecurityContext newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(newContext);
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            throw new SecurityException("Failed to store authentication context", e);
        }
    }
}
