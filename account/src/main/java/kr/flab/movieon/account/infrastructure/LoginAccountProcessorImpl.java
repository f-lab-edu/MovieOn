package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class LoginAccountProcessorImpl implements LoginAccountProcessor {

    private final AuthenticationManager authenticationManager;

    public LoginAccountProcessorImpl(
        AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AccountContext login(String userId, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userId, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (AccountContext) authentication.getPrincipal();
    }
}
