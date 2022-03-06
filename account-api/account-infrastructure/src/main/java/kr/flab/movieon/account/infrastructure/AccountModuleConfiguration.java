package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.LoginAccountProcessor;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.infrastructure.jpa.AccountRepositoryAdapter;
import kr.flab.movieon.account.infrastructure.jpa.RefreshTokenInfoRepositoryAdapter;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenExtractor;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenGenerator;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenParser;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenReIssuer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
    AccountRepositoryAdapter.class,
    DelegatePasswordEncrypter.class,
    RegisterAccountProcessor.class,
    LoginAccountProcessor.class,
    JwtTokenGenerator.class,
    JwtTokenParser.class,
    JwtTokenExtractor.class,
    JwtTokenReIssuer.class,
    RefreshTokenInfoRepositoryAdapter.class
})
@Configuration
public class AccountModuleConfiguration {

}
