package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.flab.movieon.common.AbstractAggregateRoot;
import kr.flab.movieon.payment.domain.event.PaymentCompletedEvent;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentStatusException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends AbstractAggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long purchaseId;

    private Long purchaserId;

    private String purchaseName;

    private Long quantity;

    private BigDecimal amount;

    private Type type;

    private Status status;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    private Payment(Long purchaseId, String purchaseName, Long purchaserId, BigDecimal amount,
        Type type) {
        this.purchaseId = purchaseId;
        this.purchaseName = purchaseName;
        this.purchaserId = purchaserId;
        this.amount = amount;
        this.type = type;
        this.status = Status.PENDING;
        this.quantity = 1L;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public static Payment create(Long purchaseId, String purchaseName, Long purchaserId,
        BigDecimal amount, Type type) {

        return new Payment(purchaseId, purchaseName, purchaserId, amount, type);
    }

    public void complete() {
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
        FAILED("결제실패"),
        INVALID("위변조검증실패");

        private final String description;

        Status(String description) {
            this.description = description;
        }
    }

    public enum Type {
        CARD, TOSS, KAKAO_PAY, NAVER_PAY
    }
}
