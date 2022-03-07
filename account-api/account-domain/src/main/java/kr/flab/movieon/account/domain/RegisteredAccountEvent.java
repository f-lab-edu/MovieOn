package kr.flab.movieon.account.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisteredAccountEvent implements DomainEvent {

    private final Long accountId;
    private final String username;
    private final String email;
    private final String emailCheckToken;
    private final Date occurredOn;

    public RegisteredAccountEvent(Account account) {
        this.accountId = account.getId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.emailCheckToken = account.getEmailCheckToken();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public String getEmail() {
        return email;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getEmailCheckToken() {
        return emailCheckToken;
    }

    public String getUsername() {
        return username;
    }
}
