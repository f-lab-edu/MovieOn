package kr.flab.movieon.order.infrastructure;

import kr.flab.movieon.order.domain.PaymentCompletedEvent;
import kr.flab.movieon.order.domain.PaymentCompletedProcessor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public final class OrderEventHandler {

    private final PaymentCompletedProcessor processor;
    private final ApplicationEventPublisher publisher;
    private final TransactionTemplate transactionTemplate;

    public OrderEventHandler(PaymentCompletedProcessor processor,
        ApplicationEventPublisher publisher,
        TransactionTemplate transactionTemplate) {
        this.processor = processor;
        this.publisher = publisher;
        this.transactionTemplate = transactionTemplate;
    }

    @EventListener
    public void handle(PaymentCompletedEvent event) {
        var order = transactionTemplate.execute(status -> processor.pay(event));
        order.pollAllEvents().forEach(publisher::publishEvent);
    }
}
