package kr.flab.movieon.security.domain;

public interface TokenExtractor {

    String extract(String payload);
}
