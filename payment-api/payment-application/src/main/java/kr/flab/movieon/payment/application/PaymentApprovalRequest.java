package kr.flab.movieon.payment.application;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class PaymentApprovalRequest {

    @NotBlank
    private Long purchaseId;

    @NotBlank
    private String amount;

    @NotBlank
    private String pgToken;

    @NotBlank
    private String paymentType;

}
