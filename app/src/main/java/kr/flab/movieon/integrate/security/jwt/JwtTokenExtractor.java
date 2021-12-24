package kr.flab.movieon.integrate.security.jwt;

import kr.flab.movieon.integrate.security.domain.TokenExtractor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public final class JwtTokenExtractor implements TokenExtractor {

    public static final String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank.");
        }

        if (!header.startsWith(HEADER_PREFIX)) {
            throw new AuthenticationServiceException("Invalid authorization header.");
        }

        return header.substring(HEADER_PREFIX.length());
    }
}

