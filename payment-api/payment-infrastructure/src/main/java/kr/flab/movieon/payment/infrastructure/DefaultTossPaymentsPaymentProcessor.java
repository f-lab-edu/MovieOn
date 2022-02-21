package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.TossPayments;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentApprovalCommand;
import kr.flab.movieon.payment.domain.TossPaymentsPaymentProcessor;

public final class DefaultTossPaymentsPaymentProcessor implements TossPaymentsPaymentProcessor {

    @Override
    public TossPayments payed(TossPaymentsPaymentApprovalCommand command) {
        return null;
    }
}
