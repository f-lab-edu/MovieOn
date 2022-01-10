package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.flab.movieon.common.AbstractAggregateRoot;
import kr.flab.movieon.payment.domain.event.PaymentCompletedEvent;
import kr.flab.movieon.payment.domain.event.PaymentPendingEvent;
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

    private String purchaseToken;

    private Long quantity;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private String lastTransactionId = "";

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    private Payment(Long purchaseId, String purchaseName, Long purchaserId, BigDecimal amount,
        PaymentMethod paymentMethod) {
        this.purchaseId = purchaseId;
        this.purchaseName = purchaseName;
        this.purchaserId = purchaserId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
        this.quantity = 1L;
    }

    public static Payment create(Long purchaseId, String purchaseName, Long purchaserId,
        BigDecimal amount, PaymentMethod paymentMethod) {
        var payment = new Payment(purchaseId, purchaseName, purchaserId, amount, paymentMethod);
        payment.generatePurchaseToken();

        return payment;
    }

    public void readyPayment(String transactionId, String redirectUrl) {
        if (isPaymentPending() && this.lastTransactionId.isEmpty()) {
            this.lastTransactionId = transactionId;
            this.registerEvent(new PaymentPendingEvent(this, redirectUrl));
        }
    }

    public String getLastTransactionId() {
        return this.lastTransactionId;
    }

    public void complete() {
        this.lastTransactionId = "";
        this.status = PaymentStatus.PAYED;
        this.registerEvent(new PaymentCompletedEvent(this));
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public boolean isValidPurchaseToken(String token) {
        return this.purchaseToken.equals(token);
    }

    public BigDecimal calculateTotalAmount() {
        return this.getAmount();
    }

    public boolean isPaymentPending() {
        return this.status.equals(PaymentStatus.PENDING);
    }

    private void generatePurchaseToken() {
        this.purchaseToken = UUID.randomUUID().toString();
    }

    public BigDecimal calculateTaxFreeAmount() {
        // TODO 세금을 제외한 비과세 금액계산
        return this.getAmount();
    }

    public void fail() {
        this.lastTransactionId = "";
        this.status = PaymentStatus.INVALID;
    }
}
