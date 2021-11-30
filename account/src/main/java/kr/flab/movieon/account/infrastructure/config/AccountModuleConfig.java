package kr.flab.movieon.account.infrastructure.config;

import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.domain.RoleRepository;
import kr.flab.movieon.account.infrastructure.LoginAccountProcessorImpl;
import kr.flab.movieon.account.infrastructure.RegisterAccountProcessorImpl;
import kr.flab.movieon.account.infrastructure.security.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AccountModuleConfig {

    @Bean
    public LoginAccountProcessor loginAccountProcessor(
        AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        return new LoginAccountProcessorImpl(authenticationManager, tokenUtils);
    }

    @Bean
    public RegisterAccountProcessor registerAccountProcessor(
        AccountRepository accountRepository, RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {
        return new RegisterAccountProcessorImpl(accountRepository, roleRepository, passwordEncoder);
    }
}
