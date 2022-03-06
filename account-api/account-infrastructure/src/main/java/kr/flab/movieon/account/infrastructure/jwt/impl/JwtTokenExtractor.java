package kr.flab.movieon.account.infrastructure.jwt.impl;

import java.util.Optional;
import kr.flab.movieon.account.infrastructure.jwt.TokenExtractor;

public final class JwtTokenExtractor implements TokenExtractor {

    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public Optional<String> extract(String header) {
        if (header == null || header.isBlank()) {
            return Optional.empty();
        } else {
            if (header.length() < HEADER_PREFIX.length()) {
                return Optional.empty();
            } else {
                return Optional.of(header.substring(HEADER_PREFIX.length()));
            }
        }
    }
}
