package kr.flab.movieon.security.integrate.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import kr.flab.movieon.account.domain.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JwtRawToken implements Token {

    private static final Logger log = LoggerFactory.getLogger(JwtRawToken.class);

    private final String token;

    public JwtRawToken(String token) {
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parserBuilder().setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8))
                .build().parseClaimsJws(token);
        } catch (SignatureException | SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return null;
    }

    @Override
    public String getToken() {
        return this.token;
    }
}