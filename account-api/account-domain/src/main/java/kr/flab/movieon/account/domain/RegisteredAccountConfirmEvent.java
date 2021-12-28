package kr.flab.movieon.account.domain;

import lombok.Getter;

@Getter
public final class RegisteredAccountConfirmEvent {

    private final Long accountId;
    private final String email;
    private final String emailCheckToken;

    public RegisteredAccountConfirmEvent(Long accountId, String email,
        String emailCheckToken) {
        this.accountId = accountId;
        this.email = email;
        this.emailCheckToken = emailCheckToken;
    }
}
