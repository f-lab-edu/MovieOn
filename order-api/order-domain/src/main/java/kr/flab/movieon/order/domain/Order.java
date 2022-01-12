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

    private BigDecimal discountPrice;
    private LocalDateTime orderedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Order(Customer customer, String payMethod, OrderStatus status, OrderType type,
        List<OrderProduct> products, BigDecimal discountPrice) {
        this.customer = customer;
        this.payMethod = payMethod;
        this.status = status;
        this.type = type;
        this.products = products;
        this.discountPrice = discountPrice;
        registerEvent(new OrderCreatedEvent(this));
    }

    public static Order create(Customer customer, String payMethod,
        OrderType type, List<OrderProduct> products, BigDecimal discountPrice) {
        return new Order(customer, payMethod, OrderStatus.CREATED, type, products, discountPrice);
    }

    public BigDecimal calculateTotalPrice() {
        return this.products.stream()
            .map(OrderProduct::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .subtract(this.discountPrice);
    }

    public void complete() {
        this.status = OrderStatus.COMPLETED;
        registerEvent(new OrderCompletedEvent(this));
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
        registerEvent(new OrderCanceledEvent(this));
    }

    public enum OrderType {
        RENTAL, PURCHASE
    }

    public enum OrderStatus {
        CREATED("주문 생성됨"),
        COMPLETED("주문 완료됨"),
        CANCELED("주문 취소됨");

        private final String description;

        OrderStatus(String description) {
            this.description = description;
        }
    }
}
