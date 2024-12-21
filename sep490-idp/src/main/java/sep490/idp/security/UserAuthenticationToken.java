package sep490.idp.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serializable;

public class UserAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    private final UserContextData userContextData;

    public UserAuthenticationToken(UserContextData userContextData) {
        super(null);
        this.userContextData = userContextData;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userContextData;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException("Can't touch this 🎶");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserAuthenticationToken that)) return false;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(userContextData, that.userContextData).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(userContextData).toHashCode();
    }
}
