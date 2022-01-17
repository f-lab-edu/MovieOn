package kr.flab.movieon.account.infrastructure.config;

import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.PasswordEncrypter;
import kr.flab.movieon.account.domain.RegisterAccountConfirmProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.infrastructure.LoginAccountProcessorImpl;
import kr.flab.movieon.account.infrastructure.PasswordEncrypterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AccountModuleConfig {

    @Bean
    public RegisterAccountProcessor registerAccountProcessor(
        AccountRepository accountRepository, PasswordEncrypter passwordEncrypter) {
        return new RegisterAccountProcessor(accountRepository, passwordEncrypter);
    }

    @Bean
    public PasswordEncrypter passwordEncrypter(PasswordEncoder passwordEncoder) {
        return new PasswordEncrypterAdapter(passwordEncoder);
    }

    @Bean
    public LoginAccountProcessor loginAccountProcessor(
        AuthenticationManager authenticationManager) {
        return new LoginAccountProcessorImpl(authenticationManager);
    }

    @Bean
    public RegisterAccountConfirmProcessor registerAccountConfirmProcessor(
        AccountRepository accountRepository) {
        return new RegisterAccountConfirmProcessor(accountRepository);
    }

}
