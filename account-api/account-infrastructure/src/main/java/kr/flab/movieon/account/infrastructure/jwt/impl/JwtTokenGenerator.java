package kr.flab.movieon.account.infrastructure.jwt.impl;

import static java.util.stream.Collectors.toList;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfo;
import kr.flab.movieon.account.infrastructure.jwt.RefreshTokenInfoRepository;
import kr.flab.movieon.account.infrastructure.jwt.Scopes;
import kr.flab.movieon.account.infrastructure.jwt.TokenProperties;
import kr.flab.movieon.common.Role;

public final class JwtTokenGenerator implements TokenGenerator {

    private final RefreshTokenInfoRepository refreshTokenInfoRepository;
    private final TokenProperties tokenProperties;

    public JwtTokenGenerator(RefreshTokenInfoRepository refreshTokenInfoRepository,
        TokenProperties tokenProperties) {
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
        this.tokenProperties = tokenProperties;
    }

    @Override
    public Tokens generate(String email, Set<Role> roles) {
        return new Tokens(generateAccessToken(email, roles), generateRefreshToken(email));
    }

    private String generateAccessToken(String email, Set<Role> roles) {
        var claims = new HashMap<String, Object>();
        claims.put("email", email);
        claims.put("scopes", getAuthorities(roles));
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

    private List<String> getAuthorities(Set<Role> roles) {
        return roles.stream()
            .map(r -> "ROLE_" + r.name()).collect(toList());
    }

    private String generateRefreshToken(String email) {
        var claims = new HashMap<String, Object>();
        claims.put("email", email);
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
