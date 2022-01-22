package kr.flab.movieon.order.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PaymentCompletedProcessor {

    private final OrderRepository orderRepository;

    public PaymentCompletedProcessor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order payed(PaymentCompletedEvent event) {
        Order order = orderRepository.findByOrderId(event.getOrderId());
        try {
            order.payed(event.getPayedAmount());
            return order;
        } catch (IllegalStateException e) {
            log.debug(e.getMessage());
            order.cancel();
            return order;
        }
    }
}
