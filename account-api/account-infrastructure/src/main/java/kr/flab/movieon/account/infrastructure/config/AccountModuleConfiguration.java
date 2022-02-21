package kr.flab.movieon.account.infrastructure.config;

import kr.flab.movieon.account.domain.RegisterAccountConfirmProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.infrastructure.LoginAccountProcessorImpl;
import kr.flab.movieon.account.infrastructure.PasswordEncrypterAdapter;
import kr.flab.movieon.account.infrastructure.jpa.AccountRepositoryAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    RegisterAccountProcessor.class,
    RegisterAccountConfirmProcessor.class,
    PasswordEncrypterAdapter.class,
    LoginAccountProcessorImpl.class,
    AccountRepositoryAdapter.class
})
@Configuration
public class AccountModuleConfiguration {

}
