package kr.flab.movieon.notification.integrate;

import lombok.Getter;

@Getter
public final class Account {

    private final String email;
    private final String userId;

    public Account(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }
}
