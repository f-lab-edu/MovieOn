package kr.flab.movieon.integrate.security.domain;

public interface TokenExtractor {

    String extract(String payload);
}
