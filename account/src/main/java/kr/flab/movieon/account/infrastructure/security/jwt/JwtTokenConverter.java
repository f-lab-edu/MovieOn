package kr.flab.movieon.account.infrastructure.security.jwt;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.infrastructure.config.SecurityAppProperties;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.infrastructure.security.domain.Token;
import kr.flab.movieon.account.infrastructure.security.domain.TokenConverter;
import kr.flab.movieon.account.infrastructure.security.domain.TokenExtractor;
import kr.flab.movieon.account.infrastructure.security.domain.TokenGenerator;
import kr.flab.movieon.account.infrastructure.security.domain.TokenVerifier;
import kr.flab.movieon.account.infrastructure.security.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenConverter implements TokenConverter {

    private final AccountRepository accountRepository;
    private final SecurityAppProperties properties;
    private final TokenGenerator tokenGenerator;
    private final TokenVerifier tokenVerifier;
    private final TokenExtractor tokenExtractor;

    public JwtTokenConverter(AccountRepository accountRepository,
        SecurityAppProperties properties,
        TokenGenerator tokenGenerator,
        TokenVerifier tokenVerifier,
        TokenExtractor tokenExtractor) {
        this.accountRepository = accountRepository;
        this.properties = properties;
        this.tokenGenerator = tokenGenerator;
        this.tokenVerifier = tokenVerifier;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Token convert(String payload) {
        var rawJwtToken = new JwtRawToken(tokenExtractor.extract(payload));
        var refreshToken = RefreshToken.create(rawJwtToken,
                properties.getBase64TokenSigningKey())
            .orElseThrow(InvalidTokenException::new);

        if (!tokenVerifier.verify(refreshToken.getJti())) {
            throw new InvalidTokenException();
        }

        var userId = refreshToken.getSubject();
        Account account = accountRepository.findByUserId(userId)
            .orElseThrow(IllegalArgumentException::new);

        return tokenGenerator.createAccessToken(new AccountContext(account));
    }

}
