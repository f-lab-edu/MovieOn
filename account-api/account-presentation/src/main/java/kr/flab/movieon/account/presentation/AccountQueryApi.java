package kr.flab.movieon.account.presentation;

import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class AccountQueryApi {

    private final AccountFacade accountFacade;

    public AccountQueryApi(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @GetMapping("/api/v1/accounts/me")
    public ResponseEntity<ApiResponse<?>> find(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ResponseEntity
            .ok(ApiResponse.success(accountFacade.find(authenticatedUser.getId())));
    }
}
