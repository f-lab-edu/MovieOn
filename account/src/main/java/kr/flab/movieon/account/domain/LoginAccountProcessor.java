package kr.flab.movieon.account.domain;

public interface LoginAccountProcessor {

    AccountDto authenticate(String username, String password);
}
