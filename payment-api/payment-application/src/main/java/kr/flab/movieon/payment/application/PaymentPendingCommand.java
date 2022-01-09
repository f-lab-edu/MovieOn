package kr.flab.movieon.payment.application;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class PaymentPendingCommand {

    @NotBlank
    private Long purchaseId;

    @NotBlank
    private String paymentMethod;
}
