package kr.flab.movieon.account.infrastructure.security.jwt;

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
import kr.flab.movieon.account.infrastructure.config.SecurityAppProperties;
import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;
import kr.flab.movieon.account.infrastructure.security.domain.AuthInfo;
import kr.flab.movieon.account.infrastructure.security.domain.AuthInfoRepository;
import kr.flab.movieon.account.infrastructure.security.domain.Token;
import kr.flab.movieon.account.infrastructure.security.domain.TokenGenerator;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenGenerator implements TokenGenerator {

    private final AuthInfoRepository authInfoRepository;
    private final SecurityAppProperties properties;

    public JwtTokenGenerator(AuthInfoRepository authInfoRepository,
        SecurityAppProperties properties) {
        this.authInfoRepository = authInfoRepository;
        this.properties = properties;
    }

    public Token createAccessToken(AccountContext accountContext) {
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
                .plusMinutes(properties.getTokenExpirationTime())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(Keys.hmacShaKeyFor(
                    properties.getBase64TokenSigningKey().getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact();

        return new JwtToken(token, claims);
    }

    public Token createRefreshToken(AccountContext accountContext) {
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
                .plusMinutes(properties.getRefreshExpirationTime())
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(Keys.hmacShaKeyFor(
                    properties.getBase64TokenSigningKey().getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact();

        authInfoRepository.save(new AuthInfo(randomJti));

        return new JwtToken(token, claims);
    }
}
