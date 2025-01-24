package sep490.idp.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.Map;

@Getter
public class JwtAuthenticationTokenDecorator extends JwtAuthenticationToken {
    
    private final JwtAuthenticationToken delegate;
    private final UserContextData principal;
    
    public JwtAuthenticationTokenDecorator(JwtAuthenticationToken jwt, UserContextData principal) {
        super(jwt.getToken());
        this.delegate = jwt;
        this.principal = principal;
    }
    
    @Override
    public Object getPrincipal() {
        return principal;
    }
    
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }
    
    @Override
    public boolean isAuthenticated() {
        return delegate.isAuthenticated();
    }
    
    @Override
    public void setDetails(Object details) {
        delegate.setDetails(details);
    }
    
    @Override
    public void eraseCredentials() {
        delegate.eraseCredentials();
    }
    
    @Override
    public Map<String, Object> getTokenAttributes() {
        return delegate.getTokenAttributes();
    }
    
    @Override
    public String getName() {
        return delegate.getName();
    }
    
    @Override
    public Object getCredentials() {
        return delegate.getCredentials();
    }
    
    @Override
    public Object getDetails() {
        return delegate.getDetails();
    }
    
    @Override
    public String toString() {
        return delegate.toString();
    }
    
    @Override
    public boolean implies(Subject subject) {
        return delegate.implies(subject);
    }
    
    @Override
    public void setAuthenticated(boolean authenticated) {
        delegate.setAuthenticated(authenticated);
    }
}
