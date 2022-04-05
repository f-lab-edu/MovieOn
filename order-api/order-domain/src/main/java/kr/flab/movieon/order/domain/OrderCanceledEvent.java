package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class OrderCanceledEvent implements DomainEvent {

    private final String orderId;
    private final String accountId;
    private final Date occurredOn;
    private final BigDecimal useOfPoint;

    public OrderCanceledEvent(Order order) {
        this.orderId = order.getOrderKey();
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

    public String getAccountId() {
        return accountId;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }
}
