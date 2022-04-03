package kr.flab.movieon.account.presentation;

import java.net.URI;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.command.ConfirmRegisterAccountCommand;
import kr.flab.movieon.account.application.command.LoginAccountCommand;
import kr.flab.movieon.account.application.command.RegisterAccountCommand;
import kr.flab.movieon.account.presentation.request.ConfirmRegisterAccountRequest;
import kr.flab.movieon.account.presentation.request.LoginAccountRequest;
import kr.flab.movieon.account.presentation.request.RegisterAccountRequest;
import kr.flab.movieon.account.presentation.response.TokenResponse;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class AuthenticationApi implements AuthenticationSpecification {

    private final AccountFacade accountFacade;

    public AuthenticationApi(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @Override
    public void register(RegisterAccountRequest request) {
        accountFacade.register(new RegisterAccountCommand(request.getEmail(),
            request.getPassword(),
            request.getUsername()));
    }

    @Override
    public ResponseEntity<Void> registerConfirm(ConfirmRegisterAccountRequest request) {
        accountFacade.registerConfirm(
            new ConfirmRegisterAccountCommand(request.getToken(), request.getEmail()));

        var uri = URI.create("http://localhost:3000/login");
        var header = new HttpHeaders();
        header.setLocation(uri);
        return new ResponseEntity<>(header, HttpStatus.SEE_OTHER);
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<TokenResponse>> login(LoginAccountRequest request) {
        var tokens = accountFacade.login(
            new LoginAccountCommand(request.getEmail(), request.getPassword()));
        return ResponseEntity.ok(ApiResponseEnvelop.success(
            new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken())));
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<TokenResponse>> reIssuance(String payload) {
        var tokens = accountFacade.reIssuance(payload);
        return ResponseEntity.ok(ApiResponseEnvelop.success(
            new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken())));
    }
}
