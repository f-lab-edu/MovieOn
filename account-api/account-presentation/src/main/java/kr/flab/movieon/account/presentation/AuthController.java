package kr.flab.movieon.account.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.payload.AccountTokenResponse;
import kr.flab.movieon.account.application.payload.LoginAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountCommand;
import kr.flab.movieon.account.application.payload.RegisterAccountResponse;
import kr.flab.movieon.common.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(
    path = "/api/auth",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AuthController {

    AccountFacade accountFacade;

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
    public ApiResponse<AccountTokenResponse> regenerateToken(HttpServletRequest request) {
        return ApiResponse.success(accountFacade.refresh(request.getHeader("Authorization")));
    }
}
