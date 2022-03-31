package kr.flab.movieon.query.modules.account;

import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public final class FindAccountApi {

    private final AccountReader accountReader;

    public FindAccountApi(AccountReader accountReader) {
        this.accountReader = accountReader;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> findInfo(
        @AuthenticationPrincipal AuthenticatedUser user) {
        var account = accountReader.findByAccountId(user.getId());
        return ResponseEntity.ok(ApiResponse.success(account));
    }
}
