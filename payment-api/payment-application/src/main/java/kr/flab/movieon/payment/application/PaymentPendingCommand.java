package kr.flab.movieon.payment.application;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentPendingCommand {

    @NotBlank
    private Long purchaseId;

    @NotBlank
    private String paymentMethod;
}
