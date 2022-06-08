package ir.sharif.iam.middlewares;


import ir.sharif.iam.iam.domain.models.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class JwtAuth implements Authentication {
    private final AuthenticatedUser authenticatedUser;
    private boolean authenticated;

    public JwtAuth(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authenticatedUser.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.authenticatedUser;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        this.authenticated = false;
    }

    @Override
    public String getName() {
        return this.authenticatedUser.getUsername().toString();
    }
}
