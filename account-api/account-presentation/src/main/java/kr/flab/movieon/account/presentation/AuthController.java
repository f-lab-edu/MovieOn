package kr.flab.movieon.account.presentation;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.payload.AccountTokenResponse;
import kr.flab.movieon.account.application.payload.LoginAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountResponse;
import kr.flab.movieon.account.application.payload.RegisterConfirmCommand;
import kr.flab.movieon.common.result.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/auth",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AuthController {

    private static final String LOGIN_PAGE_URI = "http://movieon.flab.kr/login";
    private final AccountFacade accountFacade;

    public AuthController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping("/signup")
    public ApiResponse<RegisterAccountResponse> register(
        @Valid @RequestBody RegisterAccountCommand request) {
        return ApiResponse.success(accountFacade.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AccountTokenResponse> login(
        @Valid @RequestBody LoginAccountCommand request) {
        return ApiResponse.success(accountFacade.login(request));
    }

    @GetMapping("/refresh")
    public ApiResponse<AccountTokenResponse> regenerateToken(
        @RequestHeader("Authorization") String payload) {
        return ApiResponse.success(accountFacade.refresh(payload));
    }

    @GetMapping(value = "/confirm",
        produces = MediaType.ALL_VALUE,
        consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> registerConfirm(
        @ModelAttribute @Valid RegisterConfirmCommand command) throws URISyntaxException {
        accountFacade.registerConfirm(command);

        var header = new HttpHeaders();
        header.setLocation(new URI(LOGIN_PAGE_URI));
        return new ResponseEntity<>(header, HttpStatus.SEE_OTHER);
    }
}
