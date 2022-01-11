package kr.flab.movieon.payment.application;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class PaymentCompleteCommand {

    @NotBlank
    private Long purchaseId;

    @NotBlank
    private String pgToken;

}
