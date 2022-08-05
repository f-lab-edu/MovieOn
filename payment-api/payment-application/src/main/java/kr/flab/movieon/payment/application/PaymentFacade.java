package kr.flab.movieon.payment.application;

import kr.flab.movieon.payment.domain.TossPaymentsPaymentProcessor;
import org.springframework.stereotype.Service;

@Service
public final class PaymentFacade {

    private final TossPaymentsPaymentProcessor paymentProcessor;

    public PaymentFacade(TossPaymentsPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void payed(TossPaymentsPaymentApprovalCommand command) {
        paymentProcessor.payed(command.orderId(), command.paymentKey(), command.amount());
    }
}
