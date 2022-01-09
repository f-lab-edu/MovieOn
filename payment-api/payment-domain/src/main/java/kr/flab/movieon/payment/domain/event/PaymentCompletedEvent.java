package kr.flab.movieon.payment.domain.event;

import java.util.Date;
import kr.flab.movieon.common.DomainEvent;
import kr.flab.movieon.payment.domain.Payment;
import lombok.Getter;

@Getter
public final class PaymentCompletedEvent implements DomainEvent {

    private final Long paymentId;
    private final Date occurredOn;

    public PaymentCompletedEvent(Payment payment) {
        this.paymentId = payment.getId();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
