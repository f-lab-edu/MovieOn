package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long purchaseId;

    private Long purchaserId;

    private String purchaseName;

    private String purchaseToken;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

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
    }

    public static Payment create(Long purchaseId, String purchaseName, Long purchaserId,
        BigDecimal amount, PaymentMethod paymentMethod) {
        var payment = new Payment(purchaseId, purchaseName, purchaserId, amount, paymentMethod);
        payment.generatePurchaseToken();

        return payment;
    }

    public void complete() {
        this.status = PaymentStatus.PAYED;
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
}
