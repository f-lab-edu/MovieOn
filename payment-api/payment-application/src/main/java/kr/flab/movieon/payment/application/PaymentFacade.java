package kr.flab.movieon.payment.application;

import kr.flab.movieon.payment.domain.TossPaymentsPaymentProcessor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class PaymentFacade {

    private final TossPaymentsPaymentProcessor paymentProcessor;
    private final ApplicationEventPublisher publisher;

    public PaymentFacade(TossPaymentsPaymentProcessor paymentProcessor,
        ApplicationEventPublisher publisher) {
        this.paymentProcessor = paymentProcessor;
        this.publisher = publisher;
    }

    public void payed(TossPaymentsPaymentApprovalCommand command) {
        var tossPayments = paymentProcessor.payed(
            command.orderId(), command.paymentKey(), command.amount());
        tossPayments.pollAllEvents().forEach(publisher::publishEvent);
    }
}
