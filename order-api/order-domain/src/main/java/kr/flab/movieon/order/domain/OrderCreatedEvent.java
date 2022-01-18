package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;
import lombok.Getter;

@Getter
public final class OrderCreatedEvent implements DomainEvent {

    private final String orderId;
    private final Long customerId;
    private final BigDecimal useOfPoint;
    private final Date occurredOn;

    public OrderCreatedEvent(Order order) {
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomer().getAccountId();
        this.useOfPoint = order.getUseOfPoint();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
