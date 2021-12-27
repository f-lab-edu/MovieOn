package kr.flab.movieon.account.integrate;

import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.integrate.security.domain.TokenConverter;
import kr.flab.movieon.integrate.security.domain.TokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class AccountIntegrateConfig {

    @Bean
    public LoginAccountProcessor loginAccountProcessor(AuthenticationManager authenticationManager,
        TokenGenerator tokenGenerator, TokenConverter tokenConverter) {
        return new TokenLoginAccountProcessor(authenticationManager, tokenGenerator,
            tokenConverter);
    }
}
