package kr.flab.movieon.account.infrastructure.security.exception;

import kr.flab.movieon.account.infrastructure.security.domain.Token;
import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {

    private final Token token;

    public JwtExpiredTokenException(Token token, String message) {
        super(message);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
