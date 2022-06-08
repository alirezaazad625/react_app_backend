package ir.sharif.iam.iam.domain.models;


import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Getter
public class AuthenticatedUser implements Serializable {
    private final String username;
    private final Set<Permission> authorities;

    public AuthenticatedUser(final String username,
                             final Collection<Permission> authorities) {
        this.username = username;
        this.authorities = Objects.requireNonNullElse(
                new HashSet<>(authorities == null ? new ArrayList<>() : authorities),
                new HashSet<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticatedUser that = (AuthenticatedUser) o;
        return username.equals(that.username) &&
                Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authorities);
    }
}
