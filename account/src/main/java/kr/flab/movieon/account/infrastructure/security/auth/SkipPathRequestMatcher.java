package kr.flab.movieon.account.infrastructure.security.auth;

import io.jsonwebtoken.lang.Assert;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Slf4j
public class SkipPathRequestMatcher implements RequestMatcher {

    private final OrRequestMatcher matcher;
    private final RequestMatcher processingMatcher;

    public SkipPathRequestMatcher(List<String> skipUrls, String processingPath) {
        Assert.notNull(skipUrls);
        List<RequestMatcher> matchers = skipUrls.stream()
            .map(AntPathRequestMatcher::new)
            .collect(Collectors.toList());

        matcher = new OrRequestMatcher(matchers);
        processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        if (matcher.matches(httpServletRequest)) {
            return false;
        }
        return processingMatcher.matches(httpServletRequest);
    }
}

