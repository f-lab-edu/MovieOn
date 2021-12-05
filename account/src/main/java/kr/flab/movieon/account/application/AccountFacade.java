package kr.flab.movieon.account.application;

import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.presentation.payload.AccountResponse;
import kr.flab.movieon.account.presentation.payload.JwtResponse;
import kr.flab.movieon.account.presentation.payload.LoginAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountCommand;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountFacade {

    private final LoginAccountProcessor loginAccountProcessor;
    private final RegisterAccountProcessor registerAccountProcessor;
    private final AccountRepository accountRepository;

    public AccountFacade(LoginAccountProcessor loginAccountProcessor,
        RegisterAccountProcessor registerAccountProcessor,
        AccountRepository accountRepository) {
        this.loginAccountProcessor = loginAccountProcessor;
        this.registerAccountProcessor = registerAccountProcessor;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public JwtResponse login(LoginAccountCommand command) {
        var accountDto = loginAccountProcessor.authenticate(command.getUsername(),
            command.getPassword());
        return JwtResponse.builder()
            .accessToken(accountDto.getAccessToken())
            .refreshToken(accountDto.getRefreshToken())
            .username(accountDto.getUsername())
            .email(accountDto.getEmail())
            .roles(accountDto.getRoles())
            .build();
    }

    @Transactional
    public void register(RegisterAccountCommand command) {
        registerAccountProcessor.register(
            command.getUsername(),
            command.getPassword(),
            command.getEmail(),
            command.getRole()
        );
    }

    public AccountResponse findInfo(AccountContext accountContext) {
        if (accountContext == null) {
            throw new AccessDeniedException("Anonymous user require login authentication.");
        }
        return new AccountResponse(accountContext.getUsername(), accountContext.getEmail());
    }
}
