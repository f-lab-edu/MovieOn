package kr.flab.movieon.payment.application;

import java.math.BigDecimal;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentApprovalCommand;
import kr.flab.movieon.payment.domain.PaymentProcessor;
import kr.flab.movieon.payment.domain.PaymentRepository;
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
    public void approve(PaymentApprovalRequest request) {
        var command = PaymentApprovalCommand.builder()
            .paymentType(Payment.Type.valueOf(request.getPaymentType()))
            .amount(new BigDecimal(request.getAmount()))
            .orderId(request.getOrderId())
            .pgToken(request.getPgToken())
            .build();

        paymentProcessor.pay(command);

        var payment = paymentRepository.findByOrderId(command.getOrderId())
            .orElseThrow(() -> new InvalidPaymentCommandException(
                "The payment cannot be completed, Failed to complete payment."));

        payment.pollAllEvents().forEach(publisher::publishEvent);
    }
}
