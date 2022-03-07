package kr.flab.movieon.common;

import java.util.Objects;
import java.util.Set;

public final class AuthenticatedUser {

    private final String id;
    private final Set<Role> roles;

    public AuthenticatedUser(String id, Set<Role> roles) {
        this.id = id;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticatedUser that = (AuthenticatedUser) o;
        return Objects.equals(id, that.id) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roles);
    }

    @Override
    public String toString() {
        return "AuthenticatedUser{" + "id=" + id + ", roles=" + roles + '}';
    }
}

