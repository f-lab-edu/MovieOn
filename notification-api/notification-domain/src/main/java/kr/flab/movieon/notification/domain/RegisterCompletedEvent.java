package kr.flab.movieon.notification.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisterCompletedEvent implements DomainEvent {

    private final String accountId;
    private final Date occurredOn;

    public RegisterCompletedEvent(String accountId, Date occurredOn) {
        this.accountId = accountId;
        this.occurredOn = occurredOn;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "RegisterCompletedEvent{" + "accountId='" + accountId + '\'' + ", occurredOn="
            + occurredOn + '}';
    }
}
