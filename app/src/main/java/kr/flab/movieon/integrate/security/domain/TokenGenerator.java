package kr.flab.movieon.integrate.security.domain;

public interface TokenGenerator {

    Token createRefreshToken(AccountContext accountContext);

    Token createAccessToken(AccountContext accountContext);
}
