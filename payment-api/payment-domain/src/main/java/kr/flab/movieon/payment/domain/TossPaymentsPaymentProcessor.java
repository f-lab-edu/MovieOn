package kr.flab.movieon.payment.domain;

public interface TossPaymentsPaymentProcessor {

    TossPayments payed(TossPaymentsPaymentApprovalCommand command);
}
