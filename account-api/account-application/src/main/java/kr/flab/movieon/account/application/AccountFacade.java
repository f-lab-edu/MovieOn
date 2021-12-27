package kr.flab.movieon.account.application;

import kr.flab.movieon.account.application.payload.AccountResponse;
import kr.flab.movieon.account.application.payload.AccountTokenResponse;
import kr.flab.movieon.account.application.payload.LoginAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountResponse;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.common.AccountAuthentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountFacade {

    private final RegisterAccountProcessor registerAccountProcessor;
    private final LoginAccountProcessor loginAccountProcessor;

    public AccountFacade(RegisterAccountProcessor registerAccountProcessor,
        LoginAccountProcessor loginAccountProcessor) {
        this.registerAccountProcessor = registerAccountProcessor;
        this.loginAccountProcessor = loginAccountProcessor;
    }

    @Transactional(readOnly = true)
    public AccountTokenResponse login(LoginAccountCommand command) {
        var dto = loginAccountProcessor.login(command.getUserId(), command.getPassword());

        return new AccountTokenResponse(dto.getAccessToken(), dto.getRefreshToken());
    }

    @Transactional
    public RegisterAccountResponse register(RegisterAccountCommand command) {
        registerAccountProcessor.register(command.getUserId(), command.getEmail(),
            command.getPassword());

        return RegisterAccountResponse.builder().email(command.getEmail())
            .userId(command.getUserId()).build();
    }

    @Transactional
    public AccountTokenResponse refresh(String payload) {
        var dto = loginAccountProcessor.refresh(payload);
        return new AccountTokenResponse(dto.getAccessToken(), dto.getRefreshToken());
    }

    public AccountResponse findInfo(AccountAuthentication authentication) {
        if (authentication == null) {
            throw new AccessDeniedException("Anonymous user require login authentication.");
        }

        return new AccountResponse(authentication.getUserId(), authentication.getEmail());
    }
}
