package kr.flab.movieon.account.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.account.domain.AccountTokenDto;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.infrastructure.security.domain.Token;
import kr.flab.movieon.account.infrastructure.security.domain.TokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class LoginAccountProcessorImpl implements LoginAccountProcessor {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;

    public LoginAccountProcessorImpl(
        AuthenticationManager authenticationManager,
        TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public AccountTokenDto authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        List<String> roles = accountContext.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        Token accessToken = tokenGenerator.createAccessToken(accountContext);
        Token refreshToken = tokenGenerator.createRefreshToken(accountContext);

        return AccountTokenDto.builder()
            .accessToken(accessToken.getToken())
            .refreshToken(refreshToken.getToken())
            .email(accountContext.getEmail())
            .username(accountContext.getUsername())
            .roles(roles)
            .build();
    }
}
