package kr.flab.movieon.account.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import kr.flab.movieon.account.infrastructure.security.domain.AccountDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class TokenUtils {

    private final String base64Secret;
    private final long tokenValidityInMilliseconds;

    public TokenUtils(
        // #TODO application.yml의 profile값을 불러오는데 실패해서, 일단 임시로 기본값 사용.
        @Value("${jwt.base64-secret:c2VjcmV0S2V5LXRlc3QtYXV0aG9yaXphdGlvbi1qd3QtbWFuYWdlLXRva2Vu}")
            String base64Secret,
        @Value("${jwt.token-validity-in-seconds:86400}") long tokenValidityInSeconds) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    public String generateJwtToken(Authentication authentication) {
        AccountDetails accountPrincipal = (AccountDetails) authentication.getPrincipal();

        return Jwts.builder()
            .setSubject(accountPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + tokenValidityInMilliseconds))
            .signWith(Keys.hmacShaKeyFor(base64Secret.getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(base64Secret).build().parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(base64Secret).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            log.info("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
