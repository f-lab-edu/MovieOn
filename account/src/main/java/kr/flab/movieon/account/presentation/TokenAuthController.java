package kr.flab.movieon.account.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.infrastructure.security.domain.Token;
import kr.flab.movieon.account.infrastructure.security.domain.TokenConverter;
import kr.flab.movieon.account.presentation.payload.JwtResponse;
import kr.flab.movieon.account.presentation.payload.LoginAccountCommand;
import kr.flab.movieon.account.presentation.payload.RegisterAccountCommand;
import kr.flab.movieon.common.ApiResult;
import org.springframework.http.HttpStatus;
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
    private final TokenConverter tokenConverter;

    public TokenAuthController(AccountFacade accountFacade,
        TokenConverter tokenConverter) {
        this.accountFacade = accountFacade;
        this.tokenConverter = tokenConverter;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(
        @Valid @RequestBody RegisterAccountCommand request) {
        accountFacade.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResult<JwtResponse>> authenticate(
        @Valid @RequestBody LoginAccountCommand request) {
        return ResponseEntity.ok(
            ApiResult.success(accountFacade.login(request)));
    }

    @GetMapping("/token")
    public Token regenerateToken(HttpServletRequest request) {
        return tokenConverter.convertToAccessToken(request.getHeader("Authorization"));
    }

}
