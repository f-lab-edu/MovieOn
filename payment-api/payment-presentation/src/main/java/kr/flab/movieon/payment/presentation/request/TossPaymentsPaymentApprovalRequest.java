package kr.flab.movieon.payment.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import kr.flab.movieon.payment.application.TossPaymentsPaymentApprovalCommand;

@Schema(description = "결제 승인 요청")
public final class TossPaymentsPaymentApprovalRequest {

    @NotBlank
    @Schema(description = "주문ID", example = "ord_123905819893189312", required = true)
    private String orderId;

    @NotBlank
    @Schema(description = "결제키", example = "5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6",
        required = true)
    private String paymentKey;

    @Min(value = 0)
    @Schema(description = "결제금액", required = true)
    private Integer amount;

    public TossPaymentsPaymentApprovalCommand toCommand() {
        return new TossPaymentsPaymentApprovalCommand(this.orderId, this.paymentKey, this.amount);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}