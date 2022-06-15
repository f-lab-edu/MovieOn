package kr.flab.movieon.account.infrastructure.jwt;

import java.util.List;
import java.util.Objects;
import kr.flab.movieon.common.error.InvalidTokenException;

public final class RawToken {

    private final String subject;
    private final String jti;
    private final List<String> authorities;

    public RawToken(String subject, List<String> authorities) {
        this(subject, null, authorities);
    }

    public RawToken(String subject, String jti, List<String> authorities) {
        this.subject = subject;
        this.jti = jti;
        this.authorities = authorities;
    }

    public void verify(String typeName) {
        if (!isRefreshable(typeName)) {
            throw new InvalidTokenException();
        }
    }

    private boolean isRefreshable(String typeName) {
        return authorities.stream().anyMatch(scope -> scope.equals(typeName));
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public String getJti() {
        return jti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RawToken rawToken = (RawToken) o;
        return Objects.equals(subject, rawToken.subject) && Objects.equals(jti, rawToken.jti)
            && Objects.equals(authorities, rawToken.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, jti, authorities);
    }

    @Override
    public String toString() {
        return "Token{" + "subject='" + subject + '\'' + ", jti='" + jti + '\'' + ", authorities="
            + authorities + '}';
    }
}
