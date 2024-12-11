package sep490.idp.configs;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import sep490.idp.security.UserContextData;
import sep490.idp.utils.SecurityUtils;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
    
    @Nonnull
    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getUserContextData()
                            .map(UserContextData::getUsername)
                            .or(() -> Optional.of("unknown"));
    }
}
