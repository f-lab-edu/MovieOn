package kr.flab.movieon.order.domain;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import kr.flab.movieon.common.AbstractAggregateRoot;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id", callSuper = false)
public class Order extends AbstractAggregateRoot {

    private Long id;

    private Customer customer;

    private String payMethod;
    private OrderStatus status;
    private OrderType type;

    private Period periodOfUse;
    private boolean available;

    private List<OrderProduct> products;

    private LocalDateTime orderedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime expiredAt;

    private Order(Customer customer, String payMethod,
        OrderStatus status, OrderType type, Period periodOfUse,
        List<OrderProduct> products) {
        this.customer = customer;
        this.payMethod = payMethod;
        this.status = status;
        this.type = type;
        this.periodOfUse = periodOfUse;
        this.products = products;
    }

    public static Order toPurchase(Customer customer, String payMethod, OrderType type,
        Period periodOfUse, List<OrderProduct> products) {
        return new Order(customer, payMethod, OrderStatus.CREATED, type,
            periodOfUse, products);
    }

    public static Order toRental(Customer customer, String payMethod, OrderType type,
        Period periodOfUse, List<OrderProduct> products) {
        return new Order(customer, payMethod, OrderStatus.CREATED, type,
            periodOfUse, products);
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
