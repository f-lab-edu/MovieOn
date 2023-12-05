package kr.flab.movieon.account.application;

import kr.flab.movieon.account.application.command.LoginAccount;
import kr.flab.movieon.account.application.command.RegisterAccount;
import kr.flab.movieon.account.application.command.RegisterConfirm;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.TokenReIssuer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class AccountCommandExecutor {

    private final RegisterAccountProcessor registerProcessor;
    private final LoginAccountProcessor loginProcessor;
    private final ApplicationEventPublisher publisher;
    private final TransactionTemplate transactionTemplate;
    private final TokenGenerator tokenGenerator;
    private final TokenReIssuer tokenReIssuer;

    public AccountCommandExecutor(RegisterAccountProcessor registerProcessor,
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

    public void register(RegisterAccount command) {
        var account = transactionTemplate.execute(
            status -> registerProcessor.register(command.email(), command.password(),
                command.username()));
        account.pollAllEvents().forEach(publisher::publishEvent);
    }

    public void registerConfirm(RegisterConfirm command) {
        var account = transactionTemplate.execute(
            status -> registerProcessor.registerConfirm(command.token(),
                command.email()));
        account.pollAllEvents().forEach(publisher::publishEvent);
    }

    public TokenDto login(LoginAccount command) {
        var tokens = transactionTemplate.execute(status -> {
            var account = loginProcessor.login(command.email(), command.password());
            return tokenGenerator.generate(account.getEmail(), account.getRoles());
        });
        return new TokenDto(tokens.accessToken(), tokens.refreshToken());
    }

    public TokenDto reIssuance(String payload) {
        var tokens = transactionTemplate.execute(status -> tokenReIssuer.reIssuance(payload));
        return new TokenDto(tokens.accessToken(), tokens.refreshToken());
    }

    public record TokenDto(String accessToken, String refreshToken) {}
}
