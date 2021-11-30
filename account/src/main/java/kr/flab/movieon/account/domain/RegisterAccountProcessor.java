package kr.flab.movieon.account.domain;

import java.util.List;

public interface RegisterAccountProcessor {

    void register(String username, String password, String email, List<String> roles);
}
