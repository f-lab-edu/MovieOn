package kr.flab.movieon.account.application;

import kr.flab.movieon.account.application.payload.AccountResponse;
import kr.flab.movieon.account.application.payload.AccountTokenResponse;
import kr.flab.movieon.account.application.payload.LoginAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountResponse;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.domain.exception.AccountNotFoundException;
import kr.flab.movieon.account.infrastructure.TokenConverter;
import kr.flab.movieon.account.infrastructure.TokenGenerator;
import kr.flab.movieon.common.AccountAuthentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountFacade {

    private final RegisterAccountProcessor registerAccountProcessor;
    private final LoginAccountProcessor loginAccountProcessor;
    private final AccountRepository accountRepository;
    private final TokenGenerator tokenGenerator;
    private final TokenConverter tokenConverter;

    public AccountFacade(RegisterAccountProcessor registerAccountProcessor,
        LoginAccountProcessor loginAccountProcessor,
        AccountRepository accountRepository,
        TokenGenerator tokenGenerator,
        TokenConverter tokenConverter) {
        this.registerAccountProcessor = registerAccountProcessor;
        this.loginAccountProcessor = loginAccountProcessor;
        this.accountRepository = accountRepository;
        this.tokenGenerator = tokenGenerator;
        this.tokenConverter = tokenConverter;
    }

    @Transactional(readOnly = true)
    public AccountTokenResponse login(LoginAccountCommand command) {
        loginAccountProcessor.login(command.getUserId(), command.getPassword());

        var account = accountRepository.findByUserId(command.getUserId())
            .orElseThrow(AccountNotFoundException::new);

        return new AccountTokenResponse(
            tokenGenerator.createAccessToken(account).getToken(),
            tokenGenerator.createRefreshToken(account).getToken()
        );
    }

    @Transactional
    public RegisterAccountResponse register(RegisterAccountCommand command) {
        registerAccountProcessor.register(command.getUserId(), command.getEmail(),
            command.getPassword());

        return new RegisterAccountResponse(command.getUserId(), command.getEmail());
    }

    @Transactional
    public AccountTokenResponse refresh(String payload) {
        var tokens = tokenConverter.convert(payload);

        return new AccountTokenResponse(
            tokens.getAccessToken().getToken(),
            tokens.getRefreshToken().getToken()
        );
    }

    public AccountResponse findInfo(AccountAuthentication authentication) {
        if (authentication == null) {
            throw new AccessDeniedException("Anonymous user require login authentication.");
        }
        var account = accountRepository.findById(authentication.getAccountId())
            .orElseThrow(AccountNotFoundException::new);

        return new AccountResponse(account.getUserId(), account.getEmail());
    }
}
