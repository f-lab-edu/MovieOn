package kr.flab.movieon.account.infrastructure.jwt.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfo;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfoRepository;
import kr.flab.movieon.account.infrastructure.jwt.Scopes;
import kr.flab.movieon.account.infrastructure.jwt.TokenProperties;

public final class JwtTokenGenerator implements TokenGenerator {

    private final RefreshTokenInfoRepository refreshTokenInfoRepository;
    private final TokenProperties tokenProperties;

    public JwtTokenGenerator(RefreshTokenInfoRepository refreshTokenInfoRepository,
        TokenProperties tokenProperties) {
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
        this.tokenProperties = tokenProperties;
    }

    @Override
    public Tokens generate(Account account) {
        return new Tokens(generateAccessToken(account), generateRefreshToken(account));
    }

    private String generateAccessToken(Account account) {
        var claims = new HashMap<String, Object>();
        claims.put("email", account.getEmail());
        claims.put("scopes", account.getAuthorities());
        var currentTime = LocalDateTime.now();
        var key = Keys.hmacShaKeyFor(tokenProperties.getBase64TokenSigningKey()
            .getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().setClaims(claims).setIssuer(tokenProperties.getTokenIssuer())
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(
                currentTime.plusMinutes(tokenProperties.getTokenExpirationSec())
                    .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private String generateRefreshToken(Account account) {
        var claims = new HashMap<String, Object>();
        claims.put("email", account.getEmail());
        claims.put("scopes", List.of(Scopes.REFRESH_TOKEN.authority()));
        var randomJti = UUID.randomUUID().toString();
        refreshTokenInfoRepository.save(new RefreshTokenInfo(randomJti));
        var currentTime = LocalDateTime.now();
        var key = Keys.hmacShaKeyFor(tokenProperties.getBase64TokenSigningKey()
            .getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer(tokenProperties.getTokenIssuer())
            .setId(randomJti)
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime
                .plusMinutes(tokenProperties.getRefreshExpirationSec())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}
