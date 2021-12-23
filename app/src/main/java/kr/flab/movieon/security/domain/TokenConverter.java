package kr.flab.movieon.security.domain;

/**
 * Convert Refresh Token to Access Token.
 * If the conversion succeeds, the existing Refresh Token is expired.
 */
public interface TokenConverter {

    Token convert(String payload);
}
