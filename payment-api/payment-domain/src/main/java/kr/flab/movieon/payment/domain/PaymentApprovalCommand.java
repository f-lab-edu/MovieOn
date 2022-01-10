package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public final class PaymentApprovalCommand {

    private final Long purchaseId;

    private final BigDecimal amount;

    private final Payment.Type paymentType;

    private PaymentApprovalCommand(Long purchaseId, BigDecimal amount, Payment.Type paymentType) {
        this.purchaseId = purchaseId;
        this.amount = amount;
        this.paymentType = paymentType;
    }

}
