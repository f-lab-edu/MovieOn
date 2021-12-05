package kr.flab.movieon.account.infrastructure.security.domain;

public interface TokenVerifier {

    boolean verify(String jti);
}
