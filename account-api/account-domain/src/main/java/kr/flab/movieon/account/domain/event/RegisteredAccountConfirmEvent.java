package kr.flab.movieon.account.domain.event;

import java.util.Date;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisteredAccountConfirmEvent implements DomainEvent {

    private final Long accountId;
    private final String email;
    private final String emailValidationToken;
    private final Date occurredOn;

    public RegisteredAccountConfirmEvent(Account account) {
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.emailValidationToken = account.getEmailValidationToken();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailValidationToken() {
        return emailValidationToken;
    }
}
