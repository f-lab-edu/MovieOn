package kr.flab.movieon.payment.infrastructure;

public final class TossPaymentsPaymentApprovalVerifyResponse {

    private String orderId;
    private Integer totalAmount;

    public boolean isSatisfiedBy(String orderId, Integer totalAmount) {
        return this.orderId.equals(orderId)
            && this.totalAmount.intValue() == totalAmount.intValue();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}
