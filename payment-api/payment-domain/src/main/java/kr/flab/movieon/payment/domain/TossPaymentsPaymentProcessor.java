package kr.flab.movieon.payment.domain;

public interface TossPaymentsPaymentProcessor {

    TossPayments payed(String orderId, String paymentKey, Integer amount);
}
