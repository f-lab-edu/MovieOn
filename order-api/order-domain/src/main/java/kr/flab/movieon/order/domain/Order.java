package kr.flab.movieon.order.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.flab.movieon.common.KeyGenerator;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import kr.flab.movieon.order.domain.exception.AlreadyCanceledException;
import kr.flab.movieon.order.domain.exception.AmountNotMatchedException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ORDERS")
@DynamicUpdate
public class Order extends AbstractAggregateRoot {

    private static final String PREFIX = "ord_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderKey;

    private Customer customer;

    @Column(nullable = false)
    private String payMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderLineItem> lineItems = new ArrayList<>();

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
                  BigDecimal useOfPoint, List<OrderLineItem> lineItems) {
        this.orderKey = KeyGenerator.generate(PREFIX);
        this.customer = customer;
        this.payMethod = payMethod;
        this.status = status;
        this.useOfPoint = useOfPoint;
        this.lineItems = lineItems;
        this.totalAmount = calculateTotalPrice();
        registerEvent(new OrderCreatedEvent(this));
    }

    public static Order create(Customer customer, String payMethod,
                               BigDecimal useOfPoint, List<OrderLineItem> products) {
        return new Order(customer, payMethod, OrderStatus.CREATED, useOfPoint, products);
    }

    private BigDecimal calculateTotalPrice() {
        return this.lineItems.stream()
                .map(OrderLineItem::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(this.useOfPoint);
    }

    public void pay(BigDecimal payedAmount) {
        verifyPayed(payedAmount);
        verifyNotCanceled();
        this.status = OrderStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        registerEvent(new OrderCompletedEvent(this));
    }

    private void verifyNotCanceled() {
        if (this.status == OrderStatus.CANCELED) {
            throw new AlreadyCanceledException("이미 취소된 주문입니다.");
        }
    }

    private void verifyPayed(BigDecimal payedAmount) {
        if (!this.totalAmount.equals(payedAmount)) {
            throw new AmountNotMatchedException("결제 금액과 주문 금액이 일치하지 않습니다.");
        }
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
        registerEvent(new OrderCanceledEvent(this));
    }

    void setId(Long id) {
        this.id = id;
    }

    public List<Long> getItemIds() {
        return this.lineItems.stream()
                .map(OrderLineItem::getItemId)
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

    public String getOrderKey() {
        return orderKey;
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

    public List<OrderLineItem> getLineItems() {
        return lineItems;
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
