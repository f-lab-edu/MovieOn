package kr.flab.movieon.payment.domain;

public interface TossPaymentsPaymentProcessor {

    void pay(String orderId, String paymentKey, Integer amount);
}
