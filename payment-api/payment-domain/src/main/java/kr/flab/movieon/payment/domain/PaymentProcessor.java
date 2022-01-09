package kr.flab.movieon.payment.domain;

public interface PaymentProcessor {

    void pay(Long purchaseId, String purchaseToken);

    PaymentRedirectUri pending(Long purchaseId, String paymentMethod);
}
