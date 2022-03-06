package kr.flab.movieon.account.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class RegisteredAccountEvent implements DomainEvent {

    private final String email;
    private final Date occurredOn;

    public RegisteredAccountEvent(Account account) {
        this.email = account.getEmail();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
