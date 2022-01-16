package kr.flab.movieon.payment.domain;

import java.math.BigDecimal;
import kr.flab.movieon.payment.domain.Payment.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public final class PaymentApprovalCommand {

    private final Long orderId;
    private final Long purchaserId;
    private final String orderName;
    private final String pgToken;
    private final BigDecimal amount;
    private final Payment.Type paymentType;

    private PaymentApprovalCommand(Long orderId, Long purchaserId, String orderName, String pgToken,
        BigDecimal amount, Type paymentType) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.pgToken = pgToken;
        this.purchaserId = purchaserId;
        this.orderName = orderName;
    }

}
