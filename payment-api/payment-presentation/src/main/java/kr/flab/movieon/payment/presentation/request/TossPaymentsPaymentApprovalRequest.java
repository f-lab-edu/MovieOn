package kr.flab.movieon.payment.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kr.flab.movieon.payment.application.TossPaymentsPaymentApprovalCommand;

@Schema(description = "결제 승인 요청")
public record TossPaymentsPaymentApprovalRequest(
    @NotBlank
    @Schema(description = "주문ID", example = "ord_123905819893189312", required = true)
    String orderId,

    @NotBlank
    @Schema(description = "결제키", example = "5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6",
        required = true)
    String paymentKey,

    @Min(value = 0)
    @Schema(description = "결제금액", required = true)
    Integer amount
) {

    public TossPaymentsPaymentApprovalCommand toCommand() {
        return new TossPaymentsPaymentApprovalCommand(this.orderId, this.paymentKey, this.amount);
    }
}
