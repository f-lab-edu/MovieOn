package kr.flab.movieon.payment.domain;

public interface PaymentProvider {

    boolean support(Payment.Type type);

    boolean validate(PaymentApprovalCommand command, String pgToken);

    Payment pay(PaymentApprovalCommand command, String pgToken);
}
