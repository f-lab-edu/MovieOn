package kr.flab.movieon.order.application;

import kr.flab.movieon.order.application.command.CreateOrderCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class OrderFacade {

    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final TransactionTemplate transactionTemplate;
    private final ApplicationEventPublisher publisher;

    public OrderFacade(CreateOrderCommandHandler createOrderCommandHandler,
        TransactionTemplate transactionTemplate,
        ApplicationEventPublisher publisher) {
        this.createOrderCommandHandler = createOrderCommandHandler;
        this.transactionTemplate = transactionTemplate;
        this.publisher = publisher;
    }

    public String create(String accountId, CreateOrderCommand command) {
        var order = transactionTemplate.execute(status ->
            createOrderCommandHandler.create(accountId, command));
        order.pollAllEvents().forEach(publisher::publishEvent);
        return order.getOrderSubId();
    }
}
