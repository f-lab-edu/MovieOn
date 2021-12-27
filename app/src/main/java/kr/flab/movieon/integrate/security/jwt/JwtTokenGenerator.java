package kr.flab.movieon.integrate.security.jwt;

import static java.util.stream.Collectors.toList;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.infrastructure.Token;
import kr.flab.movieon.account.infrastructure.TokenGenerator;
import kr.flab.movieon.integrate.security.SecurityAppProperties;
import kr.flab.movieon.integrate.security.domain.AccountContext;
import kr.flab.movieon.integrate.security.domain.RefreshTokenInfo;
import kr.flab.movieon.integrate.security.domain.RefreshTokenInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class JwtTokenGenerator implements TokenGenerator {

    private final RefreshTokenInfoRepository refreshTokenInfoRepository;
    private final SecurityAppProperties properties;

    public JwtTokenGenerator(RefreshTokenInfoRepository refreshTokenInfoRepository,
        SecurityAppProperties properties) {
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
        this.properties = properties;
    }

    public Token createAccessToken(Account account) {
        var accountContext = new AccountContext(account);
        if (accountContext.getUsername().isBlank()) {
            throw new IllegalArgumentException("Cannot create jwt token without username");
        }
        if (accountContext.getAuthorities() == null || accountContext.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("User doesn't have any privileges");
        }

        Claims claims = Jwts.claims().setSubject(accountContext.getUsername());
        claims.put("scopes", accountContext.getAuthorities().stream()
            .map(Object::toString)
            .collect(toList())
        );

        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuer(properties.getTokenIssuer())
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime
                .plusSeconds(properties.getTokenExpirationSec())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(Keys.hmacShaKeyFor(
                    properties.getBase64TokenSigningKey().getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact();

        return new JwtToken(token, claims);
    }

    public Token createRefreshToken(Account account) {
        var accountContext = new AccountContext(account);
        if (accountContext.getUsername().isBlank()) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(accountContext.getUsername());
        claims.put("scopes", List.of(Scopes.REFRESH_TOKEN.name()));

        String randomJti = UUID.randomUUID().toString();
        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuer(properties.getTokenIssuer())
            .setId(randomJti)
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime
                .plusSeconds(properties.getRefreshExpirationSec())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(Keys.hmacShaKeyFor(
                    properties.getBase64TokenSigningKey().getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact();

        refreshTokenInfoRepository.save(new RefreshTokenInfo(randomJti));

        return new JwtToken(token, claims);
    }
}
