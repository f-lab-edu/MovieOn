package kr.flab.movieon.account.presentation;

import java.net.URI;
import javax.validation.Valid;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.command.ConfirmRegisterAccountCommand;
import kr.flab.movieon.account.application.command.LoginAccountCommand;
import kr.flab.movieon.account.application.command.RegisterAccountCommand;
import kr.flab.movieon.account.application.response.TokenResponse;
import kr.flab.movieon.common.result.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class AuthenticationApi {

    private final AccountFacade accountFacade;

    public AuthenticationApi(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping("/api/v1/auth/register")
    public void register(@RequestBody @Valid RegisterAccountCommand command) {
        accountFacade.register(command);
    }

    @GetMapping(value = "/api/v1/auth/confirm")
    public ResponseEntity<Void> registerConfirm(
        @ModelAttribute @Valid ConfirmRegisterAccountCommand command) throws Exception {
        accountFacade.registerConfirm(command);

        var uri = new URI("http://localhost:3000/login");
        var header = new HttpHeaders();
        header.setLocation(uri);
        return new ResponseEntity<>(header, HttpStatus.SEE_OTHER);
    }

    @PostMapping("/api/v1/auth/login")
    public void login(@RequestBody @Valid LoginAccountCommand command) {
        accountFacade.login(command);
    }

    @PostMapping("/api/v1/auth/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
        @RequestHeader("Authorization") String payload) {
        return ResponseEntity.ok(ApiResponse.success(accountFacade.refresh(payload)));
    }
}
