package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Payment {

    private Long id;
    private Long purchaseId;
    private Long purchaserId;
    private String purchaseName;
    private BigDecimal amount;
    private PaymentType type;
    private PaymentStatus status;

    private Payment(Long purchaseId, String purchaseName, Long purchaserId,
        BigDecimal amount, PaymentType type) {
        this.purchaseId = purchaseId;
        this.purchaseName = purchaseName;
        this.purchaserId = purchaserId;
        this.amount = amount;
        this.type = type;
        this.status = PaymentStatus.PENDING;
    }

    public static Payment create(Long purchaseId, String purchaseName, Long purchaserId,
        BigDecimal amount, PaymentType type) {
        return new Payment(purchaseId, purchaseName, purchaserId, amount, type);
    }

    public void complete() {
        this.status = PaymentStatus.PAYED;
    }

    public PaymentType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }
}
