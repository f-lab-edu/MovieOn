package kr.flab.movieon.account.infrastructure.security.auth;

import java.util.Collection;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.infrastructure.security.jwt.RawJwtToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private AccountContext principal;
    private RawJwtToken credentials;

    public JwtAuthToken(RawJwtToken rawJwtToken) {
        super(null);
        this.credentials = rawJwtToken;
        setAuthenticated(false);
    }

    public JwtAuthToken(AccountContext principal,
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
