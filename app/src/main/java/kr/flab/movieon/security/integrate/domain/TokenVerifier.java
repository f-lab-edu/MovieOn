package kr.flab.movieon.security.integrate.domain;

public interface TokenVerifier {

    boolean verify(String jti);
}
