package kr.flab.movieon.notification.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisteredAccountEvent implements DomainEvent {

    private final String accountId;
    private final String username;
    private final String email;
    private final String emailCheckToken;
    private final Date occurredOn;

    public RegisteredAccountEvent(String accountId, String username, String email,
        String emailCheckToken, Date occurredOn) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.emailCheckToken = emailCheckToken;
        this.occurredOn = occurredOn;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailCheckToken() {
        return emailCheckToken;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "RegisteredAccountEvent{" + "accountId='" + accountId + '\'' + ", username='"
            + username + '\'' + ", email='" + email + '\'' + ", emailCheckToken='" + emailCheckToken
            + '\'' + ", occurredOn=" + occurredOn + '}';
    }
}
