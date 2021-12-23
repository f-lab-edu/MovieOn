package kr.flab.movieon.account.presentation;

import kr.flab.movieon.account.application.AccountFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//    @PostMapping("/signup")
//    public ResponseEntity<ApiResponse<RegisterAccountResponse>> register(
//        @Valid @RequestBody RegisterAccountCommand request) {
//        return null;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<AccountTokenResponse>> authenticate(
//        @Valid @RequestBody LoginAccountCommand request) {
//        return null;
//    }
//
//    @GetMapping("/refresh")
//    public Token regenerateToken(HttpServletRequest request) {
//        return null;
//        //return accountFacade.refresh(request.getHeader("Authorization"));
//    }

}
