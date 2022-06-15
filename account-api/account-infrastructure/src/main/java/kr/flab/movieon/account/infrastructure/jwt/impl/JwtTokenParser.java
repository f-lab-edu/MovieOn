package kr.flab.movieon.account.infrastructure.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import kr.flab.movieon.account.infrastructure.jwt.AlreadyTokenExpiredException;
import kr.flab.movieon.account.infrastructure.jwt.RawToken;
import kr.flab.movieon.account.infrastructure.jwt.TokenParser;
import kr.flab.movieon.account.infrastructure.jwt.TokenProperties;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JwtTokenParser implements TokenParser {

    private final Logger log = LoggerFactory.getLogger(JwtTokenParser.class);

    private final TokenProperties tokenProperties;

    public JwtTokenParser(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Override
    public RawToken parse(String token) {
        try {
            Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey(tokenProperties.getBase64TokenSigningKey()))
                .build()
                .parseClaimsJws(token);
            String jti = (String) jwt.getBody().get("jti");
            if (jti == null) {
                return new RawToken((String) jwt.getBody().get("email"),
                    (List<String>) jwt.getBody().get("scopes"));
            }
            return new RawToken((String) jwt.getBody().get("email"), jti,
                (List<String>) jwt.getBody().get("scopes"));
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT Token", ex);
            throw new InvalidTokenException();
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            throw new AlreadyTokenExpiredException();
        }
    }

    private Key getKey(String signKey) {
        return Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
    }
}
