package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class OrderCreatedEvent implements DomainEvent {

    private final String orderId;
    private final String customerId;
    private final BigDecimal useOfPoint;
    private final Date occurredOn;

    public OrderCreatedEvent(Order order) {
        this.orderId = order.getOrderSubId();
        this.customerId = order.getCustomer().getAccountId();
        this.useOfPoint = order.getUseOfPoint();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getUseOfPoint() {
        return useOfPoint;
    }
}
