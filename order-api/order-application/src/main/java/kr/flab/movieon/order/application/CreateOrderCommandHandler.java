package kr.flab.movieon.order.application;

import java.util.List;
import kr.flab.movieon.order.application.command.CreateOrderCommand;
import kr.flab.movieon.order.application.command.CreateOrderCommand.CreateOrderItemOptionCommand;
import kr.flab.movieon.order.application.command.CreateOrderCommand.CreateOrderLineItemCommand;
import kr.flab.movieon.order.domain.Customer;
import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.OrderItemOption;
import kr.flab.movieon.order.domain.OrderLineItem;
import kr.flab.movieon.order.domain.OrderRepository;
import kr.flab.movieon.order.domain.OrderValidator;
import org.springframework.stereotype.Component;

@Component
public final class CreateOrderCommandHandler {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public CreateOrderCommandHandler(OrderRepository orderRepository,
        OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public Order create(String accountId, CreateOrderCommand command) {
        var order = Order.create(new Customer(accountId), command.payMethod(),
            command.useOfPoint(), mapFrom(command.lineItems()));
        orderValidator.validate(order);
        orderRepository.save(order);
        return order;
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
