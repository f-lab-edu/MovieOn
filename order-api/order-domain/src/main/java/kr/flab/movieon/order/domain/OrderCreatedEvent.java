package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.DomainEvent;
import lombok.Getter;

@Getter
public final class OrderCreatedEvent implements DomainEvent {

    private final Long orderId;
    private final Long customerId;
    private final BigDecimal discountPrice;
    private final Date occurredOn;

    public OrderCreatedEvent(Order order) {
        this.orderId = order.getId();
        this.customerId = order.getCustomer().getAccountId();
        this.discountPrice = order.getDiscountPrice();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
