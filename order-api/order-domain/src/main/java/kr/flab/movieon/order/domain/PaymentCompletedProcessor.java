package kr.flab.movieon.order.domain;

import kr.flab.movieon.order.domain.exception.AlreadyCanceledException;
import kr.flab.movieon.order.domain.exception.AmountNotMatchedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaymentCompletedProcessor {

    private static final Logger log = LoggerFactory.getLogger(PaymentCompletedProcessor.class);

    private final OrderRepository orderRepository;

    public PaymentCompletedProcessor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order pay(PaymentCompletedEvent event) {
        Order order = orderRepository.findByOrderId(event.orderId());
        try {
            order.pay(event.payedAmount());
            return order;
        } catch (AlreadyCanceledException | AmountNotMatchedException e) {
            log.debug(e.getMessage());
            order.cancel();
            return order;
        }
    }
}
