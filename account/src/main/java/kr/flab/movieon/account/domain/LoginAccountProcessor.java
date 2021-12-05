package kr.flab.movieon.account.domain;

public interface LoginAccountProcessor {

    AccountTokenDto authenticate(String username, String password);
}
