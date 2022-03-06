package kr.flab.movieon.account.infrastructure.jwt.impl;

import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.TokenReIssuer;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfoRepository;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenNotFoundException;
import kr.flab.movieon.account.infrastructure.jwt.Scopes;
import kr.flab.movieon.account.infrastructure.jwt.TokenExtractor;
import kr.flab.movieon.account.infrastructure.jwt.TokenParser;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.InvalidTokenException;

public final class JwtTokenReIssuer implements TokenReIssuer {

    private final TokenGenerator tokenGenerator;
    private final TokenExtractor tokenExtractor;
    private final TokenParser tokenParser;
    private final RefreshTokenInfoRepository refreshTokenInfoRepository;
    private final AccountRepository accountRepository;

    public JwtTokenReIssuer(TokenGenerator tokenGenerator,
        TokenExtractor tokenExtractor, TokenParser tokenParser,
        RefreshTokenInfoRepository refreshTokenInfoRepository,
        AccountRepository accountRepository) {
        this.tokenGenerator = tokenGenerator;
        this.tokenExtractor = tokenExtractor;
        this.tokenParser = tokenParser;
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Tokens reIssuance(String payload) {
        var rawToken = tokenExtractor.extract(payload);
        if (rawToken.isEmpty()) {
            throw new InvalidArgumentException();
        }
        var token = tokenParser.parse(rawToken.get());

        if (!token.isRefreshable(Scopes.REFRESH_TOKEN.authority())) {
            throw new InvalidTokenException();
        }
        if (!refreshTokenInfoRepository.existsByRefreshTokenJti(token.getJti())) {
            throw new RefreshTokenNotFoundException();
        }

        var account = accountRepository.findByEmail(token.getSubject());
        var refreshTokenJti = refreshTokenInfoRepository
            .findByRefreshTokenJti(token.getJti());
        refreshTokenJti.expire();
        return tokenGenerator.generate(account);
    }
}
