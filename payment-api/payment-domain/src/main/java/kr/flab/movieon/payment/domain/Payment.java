package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.flab.movieon.common.domain.model.AbstractAggregateRoot;
import kr.flab.movieon.payment.domain.event.PaymentCompletedEvent;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentStatusException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Payment extends AbstractAggregateRoot {

    protected Payment() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long purchaserId;

    private String orderName;

    private BigDecimal amount;

    private Type type;

    private Status status;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    private Payment(Long orderId, String orderName, Long purchaserId, BigDecimal amount,
        Type type) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.purchaserId = purchaserId;
        this.amount = amount;
        this.type = type;
        this.status = Status.PENDING;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public static Payment create(Long orderId, String orderName, Long purchaserId,
        BigDecimal amount, Type type) {

        return new Payment(orderId, orderName, purchaserId, amount, type);
    }

    public void approve() {
        if (this.status != Status.PENDING) {
            throw new InvalidPaymentStatusException("The payment cannot be switched to Complete.");
        }
        this.status = Status.PAYED;
        this.registerEvent(new PaymentCompletedEvent(this));
    }

    public void fail() {
        if (this.status != Status.PENDING) {
            throw new InvalidPaymentStatusException("The payment cannot be switched to Fail.");
        }
        this.status = Status.FAILED;
    }

    public void cancel() {
        if (this.status != Status.PAYED) {
            throw new InvalidPaymentStatusException("The payment cannot be switched to Cancel.");
        }
        this.status = Status.CANCELLED;
    }

    public BigDecimal calculateTotalAmount() {
        return this.getAmount();
    }

    public BigDecimal calculateTaxFreeAmount() {
        // TODO 세금을 제외한 비과세 금액계산
        return this.getAmount();
    }

    public enum Status {
        PENDING("결제대기중"),
        PAYED("결제완료"),
        CANCELLED("결제취소"),
        FAILED("결제실패");

        private final String description;

        Status(String description) {
            this.description = description;
        }
    }

    public enum Type {
        CARD, TOSS, KAKAO_PAY, NAVER_PAY
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public String getOrderName() {
        return orderName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
