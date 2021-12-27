package kr.flab.movieon.account.domain;

public interface LoginAccountProcessor {

    AccountTokenDto login(String userId, String password);

    AccountTokenDto refresh(String payload);
}
