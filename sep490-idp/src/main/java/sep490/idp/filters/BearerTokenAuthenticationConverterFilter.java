package sep490.idp.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import sep490.idp.repository.UserRepository;
import sep490.idp.security.JwtAuthenticationTokenDecorator;
import sep490.idp.security.UserContextData;

import java.io.IOException;

@RequiredArgsConstructor
public class BearerTokenAuthenticationConverterFilter extends OncePerRequestFilter {
    
    private final UserRepository userRepository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken authentication) {
            var email = authentication.getTokenAttributes().get("sub").toString();
            userRepository.findByEmail(email).ifPresent(user -> {
                var contextData = new UserContextData(user);
                var authenticationDecorator = new JwtAuthenticationTokenDecorator(authentication, contextData);
                SecurityContextHolder.getContext().setAuthentication(authenticationDecorator);
            });
        }
        
        filterChain.doFilter(request, response);
    }
}
