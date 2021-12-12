package kr.flab.movieon.account.infrastructure.security.auth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.flab.movieon.account.infrastructure.security.domain.TokenExtractor;
import kr.flab.movieon.account.infrastructure.security.jwt.JwtRawToken;
import kr.flab.movieon.account.infrastructure.util.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractor tokenExtractor;

    public JwtAuthenticationFilter(TokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        String tokenPayload = request.getHeader(WebUtil.AUTHORIZATION);
        JwtRawToken jwtRawToken = new JwtRawToken(tokenExtractor.extract(tokenPayload));
        JwtAuthenticationToken token = new JwtAuthenticationToken(jwtRawToken);

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
}
