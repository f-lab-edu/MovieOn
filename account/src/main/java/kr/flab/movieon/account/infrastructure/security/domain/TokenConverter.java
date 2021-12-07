package kr.flab.movieon.account.infrastructure.security.domain;

public interface TokenConverter {

    Token convert(String payload);
}
