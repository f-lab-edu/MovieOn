package kr.flab.movieon.query.modules.account;

import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.result.ApiResponseEnvelop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class AccountQueryApi implements AccountQuerySpecification {

    private final AccountReader accountReader;

    public AccountQueryApi(AccountReader accountReader) {
        this.accountReader = accountReader;
    }

    @Override
    public ResponseEntity<ApiResponseEnvelop<AccountReadModel>> findInfo(AuthenticatedUser user) {
        var account = accountReader.findByAccountId(user.getId());
        return ResponseEntity.ok(ApiResponseEnvelop.success(account));
    }
}
