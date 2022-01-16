package kr.flab.movieon.payment.domain.event;

import java.util.Date;
import kr.flab.movieon.common.DomainEvent;
import kr.flab.movieon.payment.domain.Payment;
import lombok.Getter;

@Getter
public final class PaymentCompletedEvent implements DomainEvent {

    private final Long orderId;
    private final Long accountId;
    private final String productName;
    private final String price;
    private final Date occurredOn;

    public PaymentCompletedEvent(Payment payment) {
        this.orderId = payment.getOrderId();
        this.accountId = payment.getPurchaserId();
        this.productName = payment.getOrderName();
        this.price = payment.getAmount().toString();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
