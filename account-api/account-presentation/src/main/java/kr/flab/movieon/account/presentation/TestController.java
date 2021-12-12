package kr.flab.movieon.account.presentation;

import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.presentation.payload.AccountResponse;
import kr.flab.movieon.common.AccountAuthentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/test",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    private final AccountFacade accountFacade;

    public TestController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Access";
    }

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getProfile(
        @AuthenticationPrincipal AccountAuthentication principal) {
        return null;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('PRIME_USER') or hasRole('ADMIN')")
    public String primeUserAccess() {
        return "User Access";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Access";
    }
}
