package kr.flab.movieon.payment.domain;

public interface PaymentProvider {

    boolean support(PaymentMethod paymentMethod);

    PaymentRedirectUri pending(Payment payment);

    void pay(Payment payment);
}
