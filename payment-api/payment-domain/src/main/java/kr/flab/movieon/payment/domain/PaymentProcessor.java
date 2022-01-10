package kr.flab.movieon.payment.domain;

public interface PaymentProcessor {

    void pay(Long purchaseId, String purchaseToken);

    void pending(Long purchaseId, String paymentMethod);
}
