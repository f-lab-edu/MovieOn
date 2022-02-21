package kr.flab.movieon.security.integrate.jwt;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.TokenConverter;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.domain.exception.AccountNotFoundException;
import kr.flab.movieon.security.integrate.SecurityAppProperties;
import kr.flab.movieon.security.integrate.domain.RefreshTokenInfoRepository;
import kr.flab.movieon.security.integrate.domain.TokenExtractor;
import kr.flab.movieon.security.integrate.domain.TokenVerifier;
import kr.flab.movieon.security.integrate.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenConverter implements TokenConverter {

    private final AccountRepository accountRepository;
    private final RefreshTokenInfoRepository refreshTokenInfoRepository;
    private final SecurityAppProperties properties;
    private final TokenGenerator tokenGenerator;
    private final TokenVerifier tokenVerifier;
    private final TokenExtractor tokenExtractor;

    public JwtTokenConverter(AccountRepository accountRepository,
        RefreshTokenInfoRepository refreshTokenInfoRepository,
        SecurityAppProperties properties,
        TokenGenerator tokenGenerator,
        TokenVerifier tokenVerifier,
        TokenExtractor tokenExtractor) {
        this.accountRepository = accountRepository;
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
        this.properties = properties;
        this.tokenGenerator = tokenGenerator;
        this.tokenVerifier = tokenVerifier;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Tokens convert(String payload) {
        var rawJwtToken = new JwtRawToken(tokenExtractor.extract(payload));
        var refreshToken = RefreshToken.create(rawJwtToken,
                properties.getBase64TokenSigningKey())
            .orElseThrow(InvalidTokenException::new);

        if (!tokenVerifier.verify(refreshToken.getJti())) {
            throw new InvalidTokenException();
        }

        var userId = refreshToken.getSubject();
        Account account = accountRepository.findByUserId(userId)
            .orElseThrow(AccountNotFoundException::new);

        var authEntity = refreshTokenInfoRepository.findByRefreshTokenJti(refreshToken.getJti())
            .orElseThrow(InvalidTokenException::new);
        authEntity.expire();

        return new Tokens(
            tokenGenerator.createAccessToken(account),
            tokenGenerator.createRefreshToken(account)
        );
    }
}