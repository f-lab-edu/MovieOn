package kr.flab.movieon.account.infrastructure.security.domain;

public interface TokenExtractor {

    String extract(String payload);
}
