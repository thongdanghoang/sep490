package sep490.idp.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import sep490.idp.security.UserContextData;

import java.util.Optional;

public final class SecurityUtils {
    
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
}
