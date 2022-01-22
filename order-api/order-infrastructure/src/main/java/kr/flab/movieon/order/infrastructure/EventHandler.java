package kr.flab.movieon.order.infrastructure;

import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.PaymentCompletedEvent;
import kr.flab.movieon.order.domain.PaymentCompletedProcessor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    private final PaymentCompletedProcessor processor;
    private final ApplicationEventPublisher publisher;

    public EventHandler(PaymentCompletedProcessor processor,
        ApplicationEventPublisher publisher) {
        this.processor = processor;
        this.publisher = publisher;
    }

    @EventListener
    public void handle(PaymentCompletedEvent event) {
        Order order = processor.payed(event);
        order.pollAllEvents().forEach(publisher::publishEvent);
    }
}
