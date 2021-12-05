package kr.flab.movieon.account.infrastructure.security.auth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.flab.movieon.account.infrastructure.security.domain.TokenExtractor;
import kr.flab.movieon.account.infrastructure.security.jwt.RawJwtToken;
import kr.flab.movieon.account.infrastructure.util.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;

    public JwtAuthProcessingFilter(AuthenticationFailureHandler failureHandler,
        TokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException {

        if (!WebUtil.isPost(request)) {
            throw new IllegalArgumentException("Authentication method not supported");
        }

        if (!WebUtil.isAjax(request) || !WebUtil.isContentTypeJson(request)) {
            throw new IllegalArgumentException("Authentication content type not supported");
        }

        String tokenPayload = request.getHeader(WebUtil.AUTHORIZATION);

        RawJwtToken rawJwtToken = new RawJwtToken(tokenExtractor.extract(tokenPayload));
        JwtAuthToken token = new JwtAuthToken(rawJwtToken);

        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {

        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
