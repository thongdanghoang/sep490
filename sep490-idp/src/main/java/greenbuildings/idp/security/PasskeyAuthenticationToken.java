package greenbuildings.idp.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class PasskeyAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    private final MvcUserContextData userContextData;

    public PasskeyAuthenticationToken(MvcUserContextData userContextData) {
        super(null);
        this.userContextData = userContextData;
        this.setAuthenticated(true);
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PasskeyAuthenticationToken that)) return false;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(userContextData, that.userContextData).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(userContextData).toHashCode();
    }
    
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ((MvcUserContextData) getPrincipal()).getAuthorities();
    }
}
