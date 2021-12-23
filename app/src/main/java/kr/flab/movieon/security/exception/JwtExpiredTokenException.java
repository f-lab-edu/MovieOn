package kr.flab.movieon.security.exception;

import org.springframework.security.core.AuthenticationException;

public final class JwtExpiredTokenException extends AuthenticationException {

    public JwtExpiredTokenException(String message) {
        super(message);
    }
}
