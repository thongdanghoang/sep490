package sep490.idp.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import sep490.idp.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverter
        implements Converter<Jwt, JwtAuthenticationTokenDecorator> {
    
    private final UserRepository userRepository;
    
    @Override
    public JwtAuthenticationTokenDecorator convert(Jwt source) {
        var email = Objects.requireNonNull(source.getClaims().get("sub")).toString();
        if (StringUtils.isBlank(email)) {
            return null;
        }
        
        var authoritiesClaim = Objects.requireNonNull(source.getClaims().get("authorities"));
        Set<SimpleGrantedAuthority> authorities = Collections.emptySet();
        if (authoritiesClaim instanceof List<?> authoritiesLists) {
            authorities = authoritiesLists
                    .stream()
                    .filter(String.class::isInstance)
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toUnmodifiableSet());
        }
        
        var userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            return null;
        }
        
        return new JwtAuthenticationTokenDecorator(
                source,
                new UserContextData(
                        userEntity.get(),
                        List.copyOf(authorities),
                        Collections.emptyList()
                )
        );
    }
}
