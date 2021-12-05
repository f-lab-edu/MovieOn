package kr.flab.movieon.account.infrastructure.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.infrastructure.config.SecurityAppProperties;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.infrastructure.security.jwt.RawJwtToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final SecurityAppProperties properties;

    public JwtAuthProvider(AccountRepository accountRepository,
        SecurityAppProperties properties) {
        this.accountRepository = accountRepository;
        this.properties = properties;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        RawJwtToken rawJwtToken = (RawJwtToken) authentication.getCredentials();

        Jws<Claims> jws = rawJwtToken.parseClaims(properties.getTokenSigningKey());

        if (jws == null) {
            throw new BadCredentialsException("Invalid JWT claims");
        }

        String username = jws.getBody().getSubject();

        Account account = accountRepository.findByUsername(username)
            .orElseThrow(IllegalArgumentException::new);

        AccountContext context = new AccountContext(account);
        return new JwtAuthToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthToken.class.isAssignableFrom(authentication));
    }
}
