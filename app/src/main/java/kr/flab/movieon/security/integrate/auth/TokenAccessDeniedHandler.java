package kr.flab.movieon.security.integrate.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

public final class TokenAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(TokenAccessDeniedHandler.class);

    private final HandlerExceptionResolver exceptionResolver;

    public TokenAccessDeniedHandler(HandlerExceptionResolver resolver) {
        this.exceptionResolver = resolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException {

        log.error("Access Denied : {}", accessDeniedException.getMessage());
        exceptionResolver.resolveException(request, response, null, accessDeniedException);

    }
}

