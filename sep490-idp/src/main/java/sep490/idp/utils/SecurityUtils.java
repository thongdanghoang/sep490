package sep490.idp.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.security.PasskeyAuthenticationToken;
import sep490.idp.security.UserContextData;

import java.util.List;
import java.util.Optional;

@Slf4j
public final class SecurityUtils {
    
    private static final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    
    private SecurityUtils() {
        // Utility class. No instantiation allowed.
    }
    
    public static Optional<String> getUserEmail() {
        return getUserContextData().map(UserContextData::getUsername);
    }
    
    public static Optional<UserContextData> getUserContextData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.warn("No authentication present in context");
            return Optional.empty();
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
    
    public static void storeAuthenticationToContext(PasskeyAuthenticationToken authenticationToken,
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
