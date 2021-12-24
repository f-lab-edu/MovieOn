package kr.flab.movieon.integrate.security.domain;

public interface TokenVerifier {

    boolean verify(String jti);
}
