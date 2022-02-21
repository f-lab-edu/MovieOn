package kr.flab.movieon.security.integrate.domain;

public interface TokenExtractor {

    String extract(String payload);
}
