package kr.flab.movieon.account.presentation.payload;

import lombok.Getter;

@Getter
public final class AccountResponse {

    private final String username;
    private final String email;

    public AccountResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
