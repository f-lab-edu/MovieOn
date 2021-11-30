package kr.flab.movieon.account.application;

import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.presentation.payload.JwtResponse;
import kr.flab.movieon.account.presentation.payload.LoginAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountFacade {

    private final LoginAccountProcessor loginAccountProcessor;
    private final RegisterAccountProcessor registerAccountProcessor;

    public AccountFacade(LoginAccountProcessor loginAccountProcessor,
        RegisterAccountProcessor registerAccountProcessor) {
        this.loginAccountProcessor = loginAccountProcessor;
        this.registerAccountProcessor = registerAccountProcessor;
    }

    @Transactional(readOnly = true)
    public JwtResponse login(LoginAccountCommand command) {
        var accountDto = loginAccountProcessor.authenticate(command.getUsername(),
            command.getPassword());
        return JwtResponse.builder()
            .token(accountDto.getAccessToken())
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
}
