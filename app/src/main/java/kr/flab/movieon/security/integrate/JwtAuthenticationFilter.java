package kr.flab.movieon.security.integrate;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.infrastructure.jwt.TokenExtractor;
import kr.flab.movieon.account.infrastructure.jwt.TokenParser;
import kr.flab.movieon.common.AuthenticatedUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public final class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final AccountRepository accountRepository;
    private final TokenExtractor tokenExtractor;
    private final TokenParser tokenParser;

    public JwtAuthenticationFilter(AccountRepository accountRepository,
        TokenExtractor tokenExtractor, TokenParser tokenParser) {
        this.accountRepository = accountRepository;
        this.tokenExtractor = tokenExtractor;
        this.tokenParser = tokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        tokenExtractor.extract(request.getHeader(AUTHORIZATION_HEADER)).ifPresent(rawToken -> {
            var tokenMap = tokenParser.parse(rawToken);
            String email = tokenMap.getSubject();
            var account = accountRepository.findByEmail(email);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                var context = new UsernamePasswordAuthenticationToken(
                    new AuthenticatedUser(account.getAccountId(), account.getRoles()), null,
                    Collections.emptyList());
                context.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(context);
            }
        });

        filterChain.doFilter(request, response);
    }
}
