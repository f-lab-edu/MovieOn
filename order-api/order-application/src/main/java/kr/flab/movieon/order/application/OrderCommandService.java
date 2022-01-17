package kr.flab.movieon.order.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.order.application.OrderCommandService.CreateOrderCommand.CreateOrderProduct;
import kr.flab.movieon.order.domain.Customer;
import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.OrderProduct;
import kr.flab.movieon.order.domain.OrderRepository;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    public OrderCommandService(OrderRepository orderRepository,
        ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public Order create(Long accountId, CreateOrderCommand command) {
        var order = Order.create(new Customer(accountId), command.getPayMethod(),
            command.getDiscountPrice(), mapFrom(command.getProducts()));
        orderRepository.save(order);
        order.pollAllEvents().forEach(publisher::publishEvent);
        return order;
    }

    private List<OrderProduct> mapFrom(List<CreateOrderProduct> products) {
        return products.stream()
            .map(p -> new OrderProduct(p.getProductId(), p.getProductName(), p.getPrice()))
            .collect(Collectors.toList());
    }

    @Getter
    public static final class CreateOrderCommand {
        private final String payMethod;
        private final BigDecimal discountPrice;
        private final List<CreateOrderProduct> products;

        public CreateOrderCommand(String payMethod, BigDecimal discountPrice,
            List<CreateOrderProduct> products) {
            this.payMethod = payMethod;
            this.discountPrice = discountPrice;
            this.products = products;
        }

        @Getter
        public static final class CreateOrderProduct {
            private final Long productId;
            private final String productName;
            private final BigDecimal price;

            public CreateOrderProduct(Long productId, String productName, BigDecimal price) {
                this.productId = productId;
                this.productName = productName;
                this.price = price;
            }
        }
    }
}
