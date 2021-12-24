package kr.flab.movieon.integrate.security.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
public final class TokenAccessDeniedHandler implements AccessDeniedHandler {

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

