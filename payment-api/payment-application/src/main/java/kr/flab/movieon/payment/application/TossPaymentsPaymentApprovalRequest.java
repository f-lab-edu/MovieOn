package kr.flab.movieon.payment.application;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentApprovalCommand;

public final class TossPaymentsPaymentApprovalRequest {
    @NotBlank
    private String orderId;
    @NotBlank
    private String paymentKey;
    @Min(value = 0)
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
