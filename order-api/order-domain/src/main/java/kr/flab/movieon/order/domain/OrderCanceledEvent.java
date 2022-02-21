package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class OrderCanceledEvent implements DomainEvent {

    private final String orderId;
    private final Long accountId;
    private final Date occurredOn;
    private final BigDecimal useOfPoint;

    public OrderCanceledEvent(Order order) {
        this.orderId = order.getOrderId();
        this.accountId = order.getCustomer().getAccountId();
        this.occurredOn = new Date();
        this.useOfPoint = order.getUseOfPoint();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String getOrderId() {
        return orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }
}
