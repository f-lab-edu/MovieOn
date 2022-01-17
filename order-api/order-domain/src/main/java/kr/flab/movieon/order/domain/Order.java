package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Order extends AbstractAggregateRoot {

    private Long id;

    private Customer customer;

    private String payMethod;
    private OrderStatus status;

    private List<OrderProduct> products;

    private BigDecimal useOfPoint;
    private LocalDateTime orderedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Order(Customer customer, String payMethod, OrderStatus status,
        BigDecimal useOfPoint, List<OrderProduct> products) {
        this.customer = customer;
        this.payMethod = payMethod;
        this.status = status;
        this.useOfPoint = useOfPoint;
        this.products = products;
        registerEvent(new OrderCreatedEvent(this));
    }

    public static Order create(Customer customer, String payMethod,
        BigDecimal discountPrice, List<OrderProduct> products) {
        return new Order(customer, payMethod, OrderStatus.CREATED, discountPrice, products);
    }

    public BigDecimal calculateTotalPrice() {
        return this.products.stream()
            .map(OrderProduct::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .subtract(this.useOfPoint);
    }

    public void complete() {
        this.status = OrderStatus.COMPLETED;
        registerEvent(new OrderCompletedEvent(this));
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
        registerEvent(new OrderCanceledEvent(this));
    }

    void setId(Long id) {
        this.id = id;
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
