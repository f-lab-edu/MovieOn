package kr.flab.movieon.account.presentation;

import java.net.URI;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.command.LoginAccount;
import kr.flab.movieon.account.application.command.RegisterAccount;
import kr.flab.movieon.account.application.command.RegisterConfirm;
import kr.flab.movieon.account.presentation.request.LoginAccountRequest;
import kr.flab.movieon.account.presentation.request.RegisterAccountRequest;
import kr.flab.movieon.account.presentation.request.RegisterConfirmRequest;
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
        accountFacade.register(new RegisterAccount(request.email(),
            request.password(),
            request.username()));
    }

    @Override
    public ResponseEntity<Void> registerConfirm(RegisterConfirmRequest request) {
        accountFacade.registerConfirm(
            new RegisterConfirm(request.token(), request.email()));

        var uri = URI.create("http://localhost:3000/login");
        var header = new HttpHeaders();
        header.setLocation(uri);
        return new ResponseEntity<>(header, HttpStatus.SEE_OTHER);
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<TokenResponse>> login(LoginAccountRequest request) {
        var tokens = accountFacade.login(
            new LoginAccount(request.email(), request.password()));
        return ResponseEntity.ok(ApiResponseEnvelop.success(
            new TokenResponse(tokens.accessToken(), tokens.refreshToken())));
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<TokenResponse>> reIssuance(String payload) {
        var tokens = accountFacade.reIssuance(payload);
        return ResponseEntity.ok(ApiResponseEnvelop.success(
            new TokenResponse(tokens.accessToken(), tokens.refreshToken())));
    }
}
