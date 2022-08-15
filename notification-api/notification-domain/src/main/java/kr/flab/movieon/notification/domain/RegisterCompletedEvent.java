package kr.flab.movieon.notification.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public record RegisterCompletedEvent(String accountId, Date occurredOn) implements DomainEvent {

    @Override
    public String toString() {
        return "RegisterCompletedEvent{" + "accountId='" + accountId + '\'' + ", occurredOn="
            + occurredOn + '}';
    }
}
