package kr.flab.movieon.notification.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisteredAccountConfirmEvent implements DomainEvent {

    private final Long accountId;
    private final String email;
    private final String emailCheckToken;
    private final Date occurredOn;

    public RegisteredAccountConfirmEvent(Long accountId, String email,
        String emailCheckToken, Date occurredOn) {
        this.accountId = accountId;
        this.email = email;
        this.emailCheckToken = emailCheckToken;
        this.occurredOn = occurredOn;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailCheckToken() {
        return emailCheckToken;
    }
}
