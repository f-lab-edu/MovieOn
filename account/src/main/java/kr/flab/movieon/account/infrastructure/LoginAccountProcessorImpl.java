package kr.flab.movieon.account.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.account.domain.AccountDto;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.infrastructure.security.TokenUtils;
import kr.flab.movieon.account.infrastructure.security.domain.AccountDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class LoginAccountProcessorImpl implements LoginAccountProcessor {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public LoginAccountProcessorImpl(
        AuthenticationManager authenticationManager,
        TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public AccountDto authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String generatedToken = tokenUtils.generateJwtToken(authentication);

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        List<String> roles = accountDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return AccountDto.builder()
            .accessToken(generatedToken)
            .id(accountDetails.getId())
            .email(accountDetails.getEmail())
            .username(accountDetails.getUsername())
            .roles(roles)
            .build();
    }
}
