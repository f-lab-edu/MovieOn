package kr.flab.movieon.security.integrate.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

public final class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationEntryPoint.class);

    private final HandlerExceptionResolver exceptionResolver;

    public TokenAuthenticationEntryPoint(HandlerExceptionResolver resolver) {
        this.exceptionResolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        log.error("Unauthorized error: {}", authException.getMessage());
        exceptionResolver.resolveException(request, response, null, authException);
    }

}
