package kr.flab.movieon.security.integrate;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public final class FilterChainExceptionHelper extends OncePerRequestFilter {

    private final HandlerExceptionResolver exceptionResolver;

    public FilterChainExceptionHelper(@Qualifier("handlerExceptionResolver")
        HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            exceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
