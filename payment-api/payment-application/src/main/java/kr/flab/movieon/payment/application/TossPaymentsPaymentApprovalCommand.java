package kr.flab.movieon.payment.application;

public final class TossPaymentsPaymentApprovalCommand {

    private final String orderId;
    private final String paymentKey;
    private final Integer amount;

    public TossPaymentsPaymentApprovalCommand(String orderId, String paymentKey, Integer amount) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public Integer getAmount() {
        return amount;
    }
}
