package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import kr.flab.movieon.common.AbstractAggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Order extends AbstractAggregateRoot {

    private Long id;

    private Customer customer;

    private String payMethod;
    private OrderStatus status;
    private OrderType type;

    private List<OrderProduct> products;

    private LocalDateTime orderedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Order(Customer customer, String payMethod, OrderStatus status, OrderType type,
        List<OrderProduct> products) {
        this.customer = customer;
        this.payMethod = payMethod;
        this.status = status;
        this.type = type;
        this.products = products;
        registerEvent(new OrderCreatedEvent(this));
    }

    public static Order create(Customer customer, String payMethod,
        OrderType type, List<OrderProduct> products) {
        return new Order(customer, payMethod, OrderStatus.CREATED, type, products);
    }

    public BigDecimal calculateTotalPrice() {
        return this.products.stream()
            .map(OrderProduct::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public enum OrderType {
        RENTAL, PURCHASE
    }

    public enum OrderStatus {
        CREATED("주문 생성됨"),
        PAYED("주문 결제됨"),
        COMPLETED("주문 완료됨"),
        CANCELED("주문 취소됨");

        private final String description;

        OrderStatus(String description) {
            this.description = description;
        }
    }
}
