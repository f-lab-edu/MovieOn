package kr.flab.movieon.account.application;

import java.util.ArrayList;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.infrastructure.security.domain.Token;
import kr.flab.movieon.account.infrastructure.security.domain.TokenConverter;
import kr.flab.movieon.account.infrastructure.security.domain.TokenGenerator;
import kr.flab.movieon.account.presentation.payload.AccountResponse;
import kr.flab.movieon.account.presentation.payload.AccountTokenResponse;
import kr.flab.movieon.account.presentation.payload.LoginAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountResponse;
import kr.flab.movieon.common.AccountAuthentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountFacade {

    private final LoginAccountProcessor loginAccountProcessor;
    private final RegisterAccountProcessor registerAccountProcessor;
    private final TokenConverter tokenConverter;
    private final TokenGenerator tokenGenerator;

    public AccountFacade(LoginAccountProcessor loginAccountProcessor,
        RegisterAccountProcessor registerAccountProcessor,
        TokenConverter tokenConverter,
        TokenGenerator tokenGenerator) {
        this.loginAccountProcessor = loginAccountProcessor;
        this.registerAccountProcessor = registerAccountProcessor;
        this.tokenConverter = tokenConverter;
        this.tokenGenerator = tokenGenerator;
    }

    @Transactional(readOnly = true)
    public AccountTokenResponse login(LoginAccountCommand command) {

        var accountContext = loginAccountProcessor.login(command.getUserId(),
            command.getPassword());

        var accessToken = tokenGenerator.createAccessToken(accountContext);
        var refreshToken = tokenGenerator.createRefreshToken(accountContext);

        return AccountTokenResponse.builder()
            .accessToken(accessToken.getToken())
            .refreshToken(refreshToken.getToken())
            .userId(accountContext.getUserId())
            .email(accountContext.getEmail())
            .roles(new ArrayList<>(accountContext.getRoles()))
            .build();
    }

    @Transactional
    public RegisterAccountResponse register(RegisterAccountCommand command) {
        registerAccountProcessor.register(
            command.getUserId(),
            command.getPassword(),
            command.getEmail()
        );

        return RegisterAccountResponse.builder()
            .email(command.getEmail())
            .userId(command.getUserId())
            .build();
    }

    public AccountResponse findInfo(AccountAuthentication principal) {
        if (principal == null) {
            throw new AccessDeniedException("Anonymous user require login authentication.");
        }

        var accountContext = (AccountContext) principal;
        return new AccountResponse(accountContext.getUserId(), accountContext.getEmail());
    }

    @Transactional
    public Token refresh(String token) {
        return tokenConverter.convert(token);
    }
}
