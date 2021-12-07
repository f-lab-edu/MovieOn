package kr.flab.movieon.account.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.infrastructure.security.domain.Token;
import kr.flab.movieon.account.presentation.payload.AccountTokenResponse;
import kr.flab.movieon.account.presentation.payload.LoginAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountResponse;
import kr.flab.movieon.common.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/auth",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class TokenAuthController {

    private final AccountFacade accountFacade;

    public TokenAuthController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<RegisterAccountResponse>> register(
        @Valid @RequestBody RegisterAccountCommand request) {
        return ResponseEntity.ok(
            ApiResponse.success(accountFacade.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountTokenResponse>> authenticate(
        @Valid @RequestBody LoginAccountCommand request) {
        return ResponseEntity.ok(
            ApiResponse.success(accountFacade.login(request)));
    }

    @GetMapping("/refresh")
    public Token regenerateToken(HttpServletRequest request) {
        return accountFacade.refresh(request.getHeader("Authorization"));
    }

}
