package kr.flab.movieon.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import kr.flab.movieon.security.domain.Token;

public final class JwtToken implements Token {

    private final String token;

    @JsonIgnore
    private final Claims claims;

    public JwtToken(String token, Claims claims) {
        this.token = token;
        this.claims = claims;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public Claims getClaims() {
        return claims;
    }
}
