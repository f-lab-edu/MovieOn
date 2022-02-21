package kr.flab.movieon.payment.application;

import kr.flab.movieon.payment.domain.TossPaymentsPaymentApprovalCommand;

public final class TossPaymentsPaymentApprovalRequest {
    private String orderId;
    private String paymentKey;
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
