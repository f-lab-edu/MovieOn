package kr.flab.movieon.integrate.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.List;
import java.util.Optional;
import kr.flab.movieon.account.domain.Token;
import kr.flab.movieon.integrate.security.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RefreshToken implements Token {

    private final Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    public static Optional<RefreshToken> create(JwtRawToken rawToken, String signingKey) {
        Jws<Claims> claims = rawToken.parseClaims(signingKey);
        if (claims == null) {
            throw new InvalidTokenException();
        }

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty()) {
            return Optional.empty();
        }

        if (scopes.stream().noneMatch(scope -> scope.equals(Scopes.REFRESH_TOKEN.name()))) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
    }

    public String getJti() {
        return claims.getBody().getId();
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }

    @Override
    public String getToken() {
        return "";
    }
}
