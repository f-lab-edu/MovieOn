package kr.flab.movieon.order.domain;

import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.order.domain.commands.CreateOrder;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderItemOption;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderLineItem;

public final class OrderCommandHandler {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public OrderCommandHandler(OrderRepository orderRepository,
        OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public Order create(Long accountId, CreateOrder command) {
        var order = Order.create(new Customer(accountId), command.getPayMethod(),
            command.getUseOfPoint(), mapFrom(command.getLineItems()));
        orderValidator.validate(order);
        orderRepository.save(order);
        return order;
    }

    private List<OrderLineItem> mapFrom(List<CreateOrderLineItem> items) {
        return items.stream()
            .map(p -> new OrderLineItem(p.getItemId(), p.getProductName(), p.getBasePrice(),
                mapFromOption(p.getOptions())))
            .collect(Collectors.toList());
    }

    private List<OrderItemOption> mapFromOption(List<CreateOrderItemOption> options) {
        return options.stream()
            .map(o -> new OrderItemOption(o.getOptionName(), o.getSalesPrice()))
            .collect(Collectors.toList());
    }

}
