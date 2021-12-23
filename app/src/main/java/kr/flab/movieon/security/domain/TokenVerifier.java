package kr.flab.movieon.security.domain;

public interface TokenVerifier {

    boolean verify(String jti);
}
