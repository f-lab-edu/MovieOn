package kr.flab.movieon.payment.domain;

public interface PaymentProvider {

    boolean support(PaymentMethod paymentMethod);

    void pending(Payment payment);

    void pay(Payment payment, String purchaseToken);
}
