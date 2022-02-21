package kr.flab.movieon.order.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaymentCompletedProcessor {

    private static final Logger log = LoggerFactory.getLogger(PaymentCompletedProcessor.class);

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
