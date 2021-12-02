package kr.flab.movieon.account.infrastructure.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.flab.movieon.account.infrastructure.security.domain.AccountDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtTokenAuthFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final AccountDetailsService accountDetailsService;
    private final RequestMatcher skipPathRequestMatcher;

    public JwtTokenAuthFilter(TokenUtils tokenUtils, AccountDetailsService accountDetailsService,
        RequestMatcher skipPathRequestMatcher) {
        this.tokenUtils = tokenUtils;
        this.accountDetailsService = accountDetailsService;
        this.skipPathRequestMatcher = skipPathRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseToken(request);
        if (jwt == null || !tokenUtils.validateJwtToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (skipPathRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        var accountDetails = accountDetailsService.loadUserByUsername(
            tokenUtils.getUserNameFromJwtToken(jwt));

        var authentication = new UsernamePasswordAuthenticationToken(accountDetails, null,
            accountDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
