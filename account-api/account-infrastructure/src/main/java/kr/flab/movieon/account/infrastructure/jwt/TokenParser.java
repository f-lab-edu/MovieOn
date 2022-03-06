package kr.flab.movieon.account.infrastructure.jwt;

public interface TokenParser {

    RawToken parse(String token);
}
