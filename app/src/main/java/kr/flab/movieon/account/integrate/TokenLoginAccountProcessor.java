package kr.flab.movieon.account.integrate;

import kr.flab.movieon.account.domain.AccountTokenDto;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.integrate.security.domain.AccountContext;
import kr.flab.movieon.integrate.security.domain.TokenConverter;
import kr.flab.movieon.integrate.security.domain.TokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class TokenLoginAccountProcessor implements LoginAccountProcessor {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;
    private final TokenConverter tokenConverter;

    public TokenLoginAccountProcessor(AuthenticationManager authenticationManager,
        TokenGenerator tokenGenerator,
        TokenConverter tokenConverter) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.tokenConverter = tokenConverter;
    }

    @Override
    public AccountTokenDto login(String userId, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userId, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var accountContext = (AccountContext) authentication.getPrincipal();

        var accessToken = tokenGenerator.createAccessToken(accountContext);
        var refreshToken = tokenGenerator.createRefreshToken(accountContext);
        return new AccountTokenDto(accessToken.getToken(), refreshToken.getToken());
    }

    @Override
    public AccountTokenDto refresh(String payload) {
        var tokens = tokenConverter.convert(payload);

        return new AccountTokenDto(
            tokens.getAccessToken().getToken(),
            tokens.getRefreshToken().getToken());
    }
}
