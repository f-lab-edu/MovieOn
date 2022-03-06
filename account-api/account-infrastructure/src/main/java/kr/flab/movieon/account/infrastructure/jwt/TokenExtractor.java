package kr.flab.movieon.account.infrastructure.jwt;

import java.util.Optional;

public interface TokenExtractor {

    Optional<String> extract(String payload);
}
