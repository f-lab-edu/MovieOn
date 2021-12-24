package kr.flab.movieon.integrate.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.common.ErrorCode;
import kr.flab.movieon.integrate.security.SecurityAppProperties;
import kr.flab.movieon.integrate.security.domain.AccountContext;
import kr.flab.movieon.integrate.security.exception.SystemException;
import kr.flab.movieon.integrate.security.jwt.JwtRawToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public final class JwtAuthenticationProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final SecurityAppProperties properties;

    public JwtAuthenticationProvider(AccountRepository accountRepository,
        SecurityAppProperties properties) {
        this.accountRepository = accountRepository;
        this.properties = properties;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        JwtRawToken jwtRawToken = (JwtRawToken) authentication.getCredentials();

        Jws<Claims> jws = jwtRawToken.parseClaims(properties.getBase64TokenSigningKey());
        if (jws == null) {
            throw new BadCredentialsException("Invalid JWT claims");
        }

        String userId = jws.getBody().getSubject();
        Account account = accountRepository.findByUserId(userId)
            .orElseThrow(
                () -> new SystemException("The account does not exist.", ErrorCode.UN_AUTHORIZED));

        AccountContext context = new AccountContext(account);

        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
