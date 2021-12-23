package kr.flab.movieon.security.auth;

import java.util.Collection;
import kr.flab.movieon.security.domain.AccountContext;
import kr.flab.movieon.security.jwt.JwtRawToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public final class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private AccountContext principal;
    private JwtRawToken credentials;

    public JwtAuthenticationToken(JwtRawToken jwtRawToken) {
        super(null);
        this.credentials = jwtRawToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(AccountContext principal,
        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.eraseCredentials();
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - "
                    + "use constructor JwtAuthenticationToken(principal, authorities)");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
