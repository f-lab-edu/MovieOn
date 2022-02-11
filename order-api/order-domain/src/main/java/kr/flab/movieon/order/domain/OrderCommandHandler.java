package kr.flab.movieon.order.domain;

import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.order.domain.commands.CreateOrder;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderProduct;

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
            command.getUseOfPoint(), mapFrom(command.getProducts()));
        orderValidator.validate(order);
        orderRepository.save(order);
        return order;
    }

    private List<OrderProduct> mapFrom(List<CreateOrderProduct> products) {
        return products.stream()
            .map(p -> new OrderProduct(p.getProductId(), p.getProductName(), p.getPrice()))
            .collect(Collectors.toList());
    }

}
