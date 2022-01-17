package kr.flab.movieon.notification.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;
import lombok.Getter;

@Getter
public final class RegisterCompletedEvent implements DomainEvent {

    private final Long id;
    private final Date occurredOn;

    public RegisterCompletedEvent(Long id, Date occurredOn) {
        this.id = id;
        this.occurredOn = occurredOn;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
