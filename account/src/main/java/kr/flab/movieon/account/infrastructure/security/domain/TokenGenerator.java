package kr.flab.movieon.account.infrastructure.security.domain;

public interface TokenGenerator {

    Token createRefreshToken(AccountContext accountContext);

    Token createAccessToken(AccountContext accountContext);
}
