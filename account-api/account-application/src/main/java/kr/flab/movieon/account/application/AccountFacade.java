package kr.flab.movieon.account.application;

import kr.flab.movieon.account.application.command.ConfirmRegisterAccountCommand;
import kr.flab.movieon.account.application.command.LoginAccountCommand;
import kr.flab.movieon.account.application.command.RegisterAccountCommand;
import kr.flab.movieon.account.application.response.AccountResponse;
import kr.flab.movieon.account.application.response.TokenResponse;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.TokenReIssuer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class AccountFacade {

    private final RegisterAccountProcessor registerProcessor;
    private final LoginAccountProcessor loginProcessor;
    private final ApplicationEventPublisher publisher;
    private final TransactionTemplate transactionTemplate;
    private final TokenGenerator tokenGenerator;
    private final TokenReIssuer tokenReIssuer;

    public AccountFacade(RegisterAccountProcessor registerProcessor,
        LoginAccountProcessor loginProcessor,
        ApplicationEventPublisher publisher,
        TransactionTemplate transactionTemplate,
        TokenGenerator tokenGenerator, TokenReIssuer tokenReIssuer) {
        this.registerProcessor = registerProcessor;
        this.loginProcessor = loginProcessor;
        this.publisher = publisher;
        this.transactionTemplate = transactionTemplate;
        this.tokenGenerator = tokenGenerator;
        this.tokenReIssuer = tokenReIssuer;
    }

    public void register(RegisterAccountCommand command) {
        var account = transactionTemplate.execute(
            status -> registerProcessor.register(command.getEmail(), command.getPassword(),
                command.getUsername()));
        account.pollAllEvents().forEach(publisher::publishEvent);
    }

    public void registerConfirm(ConfirmRegisterAccountCommand command) {
        var account = transactionTemplate.execute(
            status -> registerProcessor.registerConfirm(command.getToken(),
                command.getEmail()));
        account.pollAllEvents().forEach(publisher::publishEvent);
    }

    public TokenResponse login(LoginAccountCommand command) {
        var tokens = transactionTemplate.execute(status -> {
            var account = loginProcessor.login(command.getEmail(), command.getPassword());
            return tokenGenerator.generate(account);
        });
        return new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken());
    }

    public TokenResponse reIssuance(String payload) {
        var tokens = transactionTemplate.execute(status -> tokenReIssuer.reIssuance(payload));
        return new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken());
    }

    public AccountResponse find(String id) {
        return null;
    }
}
