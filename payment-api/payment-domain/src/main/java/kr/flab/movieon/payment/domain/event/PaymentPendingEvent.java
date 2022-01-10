package kr.flab.movieon.payment.domain.event;

import java.util.Date;
import kr.flab.movieon.common.DomainEvent;
import kr.flab.movieon.payment.domain.Payment;
import lombok.Getter;

@Getter
public final class PaymentPendingEvent implements DomainEvent {

    private final Long paymentId;
    private final String redirectUrl;
    private final Date occurredOn;

    public PaymentPendingEvent(Payment payment, String redirectUrl) {
        this.paymentId = payment.getId();
        this.redirectUrl = redirectUrl;
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
