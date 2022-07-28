package kr.flab.movieon.payment.application;

public record TossPaymentsPaymentApprovalCommand(String orderId, String paymentKey,
                                                 Integer amount) {

}
