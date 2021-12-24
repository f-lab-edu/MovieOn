package kr.flab.movieon.integrate.security.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
public final class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

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
