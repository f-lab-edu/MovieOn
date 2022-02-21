package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class PaymentCompletedEvent implements DomainEvent {

    private final String orderId;
    private final BigDecimal payedAmount;
    private final Date occurredOn;

    public PaymentCompletedEvent(String orderId, BigDecimal payedAmount) {
        this.orderId = orderId;
        this.payedAmount = payedAmount;
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getPayedAmount() {
        return payedAmount;
    }
}
