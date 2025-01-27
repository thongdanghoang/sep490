package commons.springfw.impl.audit;

import commons.springfw.impl.utils.SecurityUtils;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    
    @Nonnull
    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getCurrentUserEmail().or(() -> Optional.of("Anonymous"));
    }
}
