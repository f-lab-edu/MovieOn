package kr.flab.movieon.account.domain;

public interface TokenGenerator {

    Token createRefreshToken(Account account);

    Token createAccessToken(Account account);
}
