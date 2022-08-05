package kr.flab.movieon.payment.domain;

public interface TossPaymentsPaymentProcessor {

    void payed(String orderId, String paymentKey, Integer amount);
}
