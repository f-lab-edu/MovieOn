package kr.flab.movieon.account.domain;

/**
 * Convert Refresh Token to Access Token.
 * If the conversion succeeds, the existing Refresh Token is expired.
 */
public interface TokenConverter {

    Tokens convert(String payload);
}
