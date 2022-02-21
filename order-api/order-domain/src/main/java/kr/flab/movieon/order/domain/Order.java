package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import kr.flab.movieon.common.IdGenerator;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "orders")
public class Order extends AbstractAggregateRoot {

    private static final String PREFIX = "ord_";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderId;

    private Customer customer;

    @Column(nullable = false)
    private String payMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderProduct> products;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private BigDecimal useOfPoint;

    private LocalDateTime completedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    protected Order() {

    }

    private Order(Customer customer, String payMethod, OrderStatus status,
        BigDecimal useOfPoint, List<OrderProduct> products) {
        this.orderId = IdGenerator.generate(PREFIX);
        this.customer = customer;
        this.payMethod = payMethod;
        this.status = status;
        this.useOfPoint = useOfPoint;
        this.products = products;
        this.totalAmount = calculateTotalPrice();
        registerEvent(new OrderCreatedEvent(this));
    }

    public static Order create(Customer customer, String payMethod,
        BigDecimal useOfPoint, List<OrderProduct> products) {
        return new Order(customer, payMethod, OrderStatus.CREATED, useOfPoint, products);
    }

    private BigDecimal calculateTotalPrice() {
        return this.products.stream()
            .map(OrderProduct::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .subtract(this.useOfPoint);
    }

    public void payed(BigDecimal payedAmount) {
        verifyPayed(payedAmount);
        verifyNotCanceled();
        this.status = OrderStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        registerEvent(new OrderCompletedEvent(this));
    }

    private void verifyNotCanceled() {
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
    }

    private void verifyPayed(BigDecimal payedAmount) {
        if (!this.totalAmount.equals(payedAmount)) {
            throw new IllegalStateException("결제 금액과 주문 금액이 일치하지 않습니다.");
        }
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
        registerEvent(new OrderCanceledEvent(this));
    }

    void setId(Long id) {
        this.id = id;
    }

    public List<Long> getProductIds() {
        return products.stream()
            .map(OrderProduct::getProductId)
            .collect(Collectors.toList());
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

    public Long getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getUseOfPoint() {
        return useOfPoint;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
