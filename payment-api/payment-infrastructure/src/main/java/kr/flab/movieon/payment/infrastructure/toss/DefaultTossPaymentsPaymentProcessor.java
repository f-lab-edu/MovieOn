package kr.flab.movieon.payment.infrastructure.toss;

import kr.flab.movieon.payment.domain.TossPaymentsPaymentProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

public final class DefaultTossPaymentsPaymentProcessor implements TossPaymentsPaymentProcessor {

    private final TossPaymentsPaymentApprovalCommandVerifier commandVerifier;
    private final TossPaymentsPaymentApprovalProcessor approvalProcessor;
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public DefaultTossPaymentsPaymentProcessor(
        TossPaymentsPaymentApprovalCommandVerifier commandVerifier,
        TossPaymentsPaymentApprovalProcessor approvalProcessor,
        RabbitTemplate rabbitTemplate,
        @Value("${rabbitmq.exchange}") String exchange,
        @Value("${rabbitmq.routingKey}") String routingKey) {
        this.commandVerifier = commandVerifier;
        this.approvalProcessor = approvalProcessor;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void payed(String orderId, String paymentKey, Integer amount) {
        commandVerifier.verify(orderId, paymentKey, amount);
        var response = approvalProcessor
            .approval(orderId, paymentKey, amount);
        rabbitTemplate.convertAndSend(exchange, routingKey, response);
    }
}
