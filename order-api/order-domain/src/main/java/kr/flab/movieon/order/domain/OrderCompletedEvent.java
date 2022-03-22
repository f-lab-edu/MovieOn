package kr.flab.movieon.order.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class OrderCompletedEvent implements DomainEvent {

    private final String orderId;
    private final String customerId;
    private final LocalDateTime orderedAt;
    private final List<Long> items;
    private final Date occurredOn;

    public OrderCompletedEvent(Order order) {
        this.orderId = order.getOrderSubId();
        this.customerId = order.getCustomer().getAccountId();
        this.orderedAt = order.getCompletedAt();
        this.items = order.getLineItems().stream()
            .map(OrderLineItem::getItemId)
            .collect(Collectors.toList());
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

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public List<Long> getItems() {
        return items;
    }
}
