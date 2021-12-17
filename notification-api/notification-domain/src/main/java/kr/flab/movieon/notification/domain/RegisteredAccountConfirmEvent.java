package kr.flab.movieon.notification.domain;

import lombok.Getter;

@Getter
public final class RegisteredAccountConfirmEvent {

    private final Long accountId;
    private final String email;

    public RegisteredAccountConfirmEvent(Long accountId, String email) {
        this.accountId = accountId;
        this.email = email;
    }
}
