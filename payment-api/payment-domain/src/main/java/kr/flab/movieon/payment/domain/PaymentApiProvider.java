package kr.flab.movieon.payment.domain;

public interface PaymentApiProvider {

    boolean support(Payment.Type type);

    Payment pay(PaymentApprovalCommand command);
}
