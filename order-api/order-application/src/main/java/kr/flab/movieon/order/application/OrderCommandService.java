package kr.flab.movieon.order.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import kr.flab.movieon.order.application.OrderCommandService.CreateOrderCommand.CreateOrderProduct;
import kr.flab.movieon.order.domain.Customer;
import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.OrderProduct;
import kr.flab.movieon.order.domain.OrderRepository;
import kr.flab.movieon.order.domain.OrderValidator;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final ApplicationEventPublisher publisher;

    public OrderCommandService(OrderRepository orderRepository,
        OrderValidator orderValidator,
        ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.publisher = publisher;
    }

    public Order create(Long accountId, CreateOrderCommand command) {
        var order = Order.create(new Customer(accountId), command.getPayMethod(),
            command.getUseOfPoint(), mapFrom(command.getProducts()));
        if (orderValidator.validate(order)) {
            throw new IllegalStateException("요청하신 주문에 대한 검증이 실패하였습니다.");
        }
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
        private final BigDecimal useOfPoint;
        private final List<CreateOrderProduct> products;

        public CreateOrderCommand(String payMethod, BigDecimal useOfPoint,
            List<CreateOrderProduct> products) {
            this.payMethod = payMethod;
            this.useOfPoint = useOfPoint;
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
