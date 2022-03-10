package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class PaymentCompletedEvent implements DomainEvent {

    private final String orderId;
    private final String orderName;
    private final BigDecimal payedAmount;
    private final Date occurredOn;

    public PaymentCompletedEvent(String orderId, String orderName,
        BigDecimal payedAmount, Date occurredOn) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.payedAmount = payedAmount;
        this.occurredOn = occurredOn;
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

    public String getOrderName() {
        return orderName;
    }
}
