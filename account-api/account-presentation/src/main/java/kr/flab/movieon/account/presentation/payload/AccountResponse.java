package kr.flab.movieon.account.presentation.payload;

import lombok.Getter;

@Getter
public final class AccountResponse {

    private final String userId;
    private final String email;

    public AccountResponse(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
