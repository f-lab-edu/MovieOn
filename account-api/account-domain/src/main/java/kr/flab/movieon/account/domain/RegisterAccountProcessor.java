package kr.flab.movieon.account.domain;

public interface RegisterAccountProcessor {

    void register(String userId, String email, String password);
}
