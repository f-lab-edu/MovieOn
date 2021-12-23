package kr.flab.movieon.security.domain;

public interface TokenGenerator {

    Token createRefreshToken(AccountContext accountContext);

    Token createAccessToken(AccountContext accountContext);
}
