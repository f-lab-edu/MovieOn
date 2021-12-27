package kr.flab.movieon.account.infrastructure;

import kr.flab.movieon.account.domain.Account;

public interface TokenGenerator {

    Token createRefreshToken(Account account);

    Token createAccessToken(Account account);
}
