package kr.flab.movieon.payment.application;

import kr.flab.movieon.payment.domain.PaymentProcessor;
import kr.flab.movieon.payment.domain.PaymentRepository;
import kr.flab.movieon.payment.domain.event.PaymentCompletedEvent;
import kr.flab.movieon.payment.domain.event.PaymentPendingEvent;
import kr.flab.movieon.payment.domain.exception.InvalidPaymentCommandException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentFacade {

    private final PaymentProcessor paymentProcessor;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;

    public PaymentFacade(PaymentProcessor paymentProcessor,
        PaymentRepository paymentRepository,
        ApplicationEventPublisher publisher) {
        this.paymentProcessor = paymentProcessor;
        this.paymentRepository = paymentRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void pending(PaymentPendingCommand command) {
        var redirectUrl = paymentProcessor.pending(command.getPurchaseId(),
            command.getPaymentMethod());

        var payment = paymentRepository.findByPurchaseId(command.getPurchaseId())
            .orElseThrow(() -> new InvalidPaymentCommandException(
                "The payment cannot be pending, Failed to create payment."));

        publisher.publishEvent(new PaymentPendingEvent(payment, redirectUrl));
    }

    @Transactional
    public void completePayment(PaymentCompleteCommand command) {
        paymentProcessor.pay(command.getPurchaseId(), command.getPurchaseToken());

        var payment = paymentRepository.findByPurchaseId(command.getPurchaseId())
            .orElseThrow(() -> new InvalidPaymentCommandException(
                "The payment cannot be completed, Failed to complete payment."));

        publisher.publishEvent(new PaymentCompletedEvent(payment));
    }
}
