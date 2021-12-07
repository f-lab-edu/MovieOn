package kr.flab.movieon.account.domain;

import kr.flab.movieon.account.infrastructure.security.domain.AccountContext;

public interface LoginAccountProcessor {

    AccountContext login(String userId, String password);
}
