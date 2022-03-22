package kr.flab.movieon.account.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisterCompletedEvent implements DomainEvent {

    private final String accountId;
    private final Date occurredOn;

    public RegisterCompletedEvent(Account account) {
        this.accountId = account.getAccountSubId();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public String getAccountId() {
        return accountId;
    }
}
