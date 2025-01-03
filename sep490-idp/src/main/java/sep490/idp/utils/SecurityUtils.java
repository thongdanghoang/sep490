package sep490.idp.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import sep490.idp.security.UserAuthenticationToken;
import sep490.idp.security.UserContextData;

import java.util.Optional;

public final class SecurityUtils {

    private static final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private SecurityUtils() {
        // Utility class. No instantiation allowed.
    }

    public static Optional<UserContextData> getUserContextData() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserContextData currentUser) {
            return Optional.of(currentUser);
        }
        return Optional.empty();
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
