package kr.flab.movieon.account.infrastructure.jwt.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.flab.movieon.account.infrastructure.jwt.AlreadyTokenExpiredException;
import kr.flab.movieon.account.infrastructure.jwt.RawToken;
import kr.flab.movieon.account.infrastructure.jwt.TokenParser;
import kr.flab.movieon.account.infrastructure.jwt.TokenProperties;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class JwtTokenParser implements TokenParser {

    private final Logger log = LoggerFactory.getLogger(JwtTokenParser.class);

    private final TokenProperties tokenProperties;

    public JwtTokenParser(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Override
    public RawToken parse(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .verifyWith(getKey(tokenProperties.getBase64TokenSigningKey()))
                    .build()
                    .parseSignedClaims(token);

            String jti = (String) jwt.getBody().get("jti");
            if (jti == null) {
                return new RawToken((String) jwt.getPayload().get("email"),
                        (List<String>) jwt.getPayload().get("scopes"));
            }
            return new RawToken((String) jwt.getBody().get("email"), jti,
                    (List<String>) jwt.getPayload().get("scopes"));
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT Token", ex);
            throw new InvalidTokenException();
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            throw new AlreadyTokenExpiredException();
        }
    }

    private SecretKey getKey(String signKey) {
        return Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
    }
}
