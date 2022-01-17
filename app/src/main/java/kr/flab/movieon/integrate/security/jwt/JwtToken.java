package kr.flab.movieon.integrate.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import kr.flab.movieon.account.domain.Token;

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
