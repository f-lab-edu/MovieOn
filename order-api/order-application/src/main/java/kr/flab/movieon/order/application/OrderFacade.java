package kr.flab.movieon.order.application;

import kr.flab.movieon.order.application.request.CreateOrderRequest;
import kr.flab.movieon.order.domain.OrderCommandHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class OrderFacade {

    private final OrderCommandHandler orderCommandHandler;
    private final TransactionTemplate transactionTemplate;
    private final ApplicationEventPublisher publisher;

    public OrderFacade(OrderCommandHandler orderCommandHandler,
        TransactionTemplate transactionTemplate,
        ApplicationEventPublisher publisher) {
        this.orderCommandHandler = orderCommandHandler;
        this.transactionTemplate = transactionTemplate;
        this.publisher = publisher;
    }

    public String create(Long accountId, CreateOrderRequest request) {
        var order = transactionTemplate.execute(status ->
            orderCommandHandler.create(accountId, request.toCommand()));
        order.pollAllEvents().forEach(publisher::publishEvent);
        return order.getOrderId();
    }
}
