package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Payment {

    public enum PaymentType {
        // TODO 더 많은 결제 타입이 생길 수 있다
        CARD, TOSS
    }

    public enum PaymentStatus {
        PENDING("결제대기중"),
        PAYED("결제완료"),
        CANCELLED("결제취소"),
        FAILED("결제실패"),
        INVALID("위변조검증실패");

        private final String description;

        PaymentStatus(String description) {
            this.description = description;
        }
    }

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

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }
}
