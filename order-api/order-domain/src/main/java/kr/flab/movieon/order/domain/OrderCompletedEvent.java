package kr.flab.movieon.order.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.common.domain.model.DomainEvent;
import lombok.Getter;

@Getter
public final class OrderCompletedEvent implements DomainEvent {

    private final String orderId;
    private final Long customerId;
    private final LocalDateTime orderedAt;
    private final List<Long> products;
    private final Date occurredOn;

    public OrderCompletedEvent(Order order) {
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomer().getAccountId();
        this.orderedAt = order.getCompletedAt();
        this.products = order.getProducts().stream()
            .map(OrderProduct::getProductId)
            .collect(Collectors.toList());
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
