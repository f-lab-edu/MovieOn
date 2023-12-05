package kr.flab.movieon.order.application;

import kr.flab.movieon.order.application.command.CreateOrderCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class CreateOrderUsecase {

    private final OrderCommandExecutor executor;
    private final TransactionTemplate transactionTemplate;
    private final ApplicationEventPublisher publisher;

    public CreateOrderUsecase(OrderCommandExecutor executor,
                              TransactionTemplate transactionTemplate,
                              ApplicationEventPublisher publisher) {
        this.executor = executor;
        this.transactionTemplate = transactionTemplate;
        this.publisher = publisher;
    }

    public String create(String accountId, CreateOrderCommand command) {
        var order = transactionTemplate.execute(status ->
                executor.create(accountId, command)
        );
        order.pollAllEvents().forEach(publisher::publishEvent);
        return order.getOrderKey();
    }
}
