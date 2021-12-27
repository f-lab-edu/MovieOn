package kr.flab.movieon.account.presentation;

import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.payload.AccountResponse;
import kr.flab.movieon.common.AccountAuthentication;
import kr.flab.movieon.common.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(
    path = "/api",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
public final class AccountController {

    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @GetMapping("/me")
    public ApiResponse<AccountResponse> findInfo(
        @AuthenticationPrincipal AccountAuthentication authentication) {
        return ApiResponse.success(accountFacade.findInfo(authentication));
    }
}
