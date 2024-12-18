package sep490.idp.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import sep490.idp.entity.UserEntity;

import java.io.Serializable;

public class UserAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    private final UserEntity user;

    public UserAuthenticationToken(UserEntity user) {
        super(null);
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException("Can't touch this 🎶");
    }
}
