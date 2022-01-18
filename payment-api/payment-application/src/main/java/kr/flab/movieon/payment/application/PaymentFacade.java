package kr.flab.movieon.payment.application;

import java.math.BigDecimal;
import kr.flab.movieon.payment.domain.Payment;
import kr.flab.movieon.payment.domain.PaymentApprovalCommand;
import kr.flab.movieon.payment.domain.PaymentProcessor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentFacade {

    private final PaymentProcessor paymentProcessor;
    private final ApplicationEventPublisher publisher;

    public PaymentFacade(PaymentProcessor paymentProcessor,
        ApplicationEventPublisher publisher) {
        this.paymentProcessor = paymentProcessor;
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

        var payment = paymentProcessor.pay(command);

        payment.pollAllEvents().forEach(publisher::publishEvent);
    }
}
