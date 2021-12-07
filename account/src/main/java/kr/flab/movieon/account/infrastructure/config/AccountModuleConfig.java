package kr.flab.movieon.account.infrastructure.config;

import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.infrastructure.LoginAccountProcessorImpl;
import kr.flab.movieon.account.infrastructure.RegisterAccountProcessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AccountModuleConfig {

    @Bean
    public LoginAccountProcessor loginAccountProcessor(
        AuthenticationManager authenticationManager) {
        return new LoginAccountProcessorImpl(authenticationManager);
    }

    @Bean
    public RegisterAccountProcessor registerAccountProcessor(
        AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return new RegisterAccountProcessorImpl(accountRepository, passwordEncoder);
    }
}
