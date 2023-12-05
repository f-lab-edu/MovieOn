package kr.flab.movieon.order.application;

import java.util.List;
import kr.flab.movieon.order.application.command.CreateOrderCommand;
import kr.flab.movieon.order.application.command.CreateOrderItemOptionCommand;
import kr.flab.movieon.order.application.command.CreateOrderLineItemCommand;
import kr.flab.movieon.order.domain.Customer;
import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.OrderItemOption;
import kr.flab.movieon.order.domain.OrderLineItem;
import kr.flab.movieon.order.domain.OrderRepository;
import kr.flab.movieon.order.domain.OrderValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class OrderCommandExecutor {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final TransactionTemplate transactionTemplate;
    private final ApplicationEventPublisher publisher;

    public OrderCommandExecutor(OrderRepository orderRepository,
                                OrderValidator orderValidator,
                                TransactionTemplate transactionTemplate,
                                ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.transactionTemplate = transactionTemplate;
        this.publisher = publisher;
    }

    public String create(String accountId, CreateOrderCommand command) {
        var order = transactionTemplate.execute(status -> {
            var entity = Order.create(new Customer(accountId), command.payMethod(),
                    command.useOfPoint(), mapFrom(command.lineItems()));
            orderValidator.validate(entity);
            return orderRepository.save(entity);
        });
        order.pollAllEvents().forEach(publisher::publishEvent);
        return order.getOrderKey();
    }

    private List<OrderLineItem> mapFrom(List<CreateOrderLineItemCommand> items) {
        return items.stream()
                .map(p -> new OrderLineItem(p.itemId(), p.productName(), p.basePrice(),
                        mapFromOption(p.options())))
                .toList();
    }

    private List<OrderItemOption> mapFromOption(List<CreateOrderItemOptionCommand> options) {
        return options.stream()
                .map(o -> new OrderItemOption(o.optionName(), o.salesPrice()))
                .toList();
    }
}
