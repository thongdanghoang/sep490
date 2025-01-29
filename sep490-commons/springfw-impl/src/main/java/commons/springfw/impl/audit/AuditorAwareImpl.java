package commons.springfw.impl.audit;

import commons.springfw.impl.utils.SecurityUtils;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    
    @Nonnull
    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getCurrentUserEmail().or(() -> Optional.of("Anonymous"));
    }
}
