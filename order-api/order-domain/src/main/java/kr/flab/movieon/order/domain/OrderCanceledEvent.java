package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.DomainEvent;

public final class OrderCanceledEvent implements DomainEvent {

    private final Long orderId;
    private final Long accountId;
    private final Date occurredOn;
    private final BigDecimal discountPrice;

    public OrderCanceledEvent(Order order) {
        this.orderId = order.getId();
        this.accountId = order.getCustomer().getAccountId();
        this.occurredOn = new Date();
        this.discountPrice = order.getDiscountPrice();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
