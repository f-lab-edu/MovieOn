package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.domain.TossPayments;
import kr.flab.movieon.payment.domain.TossPaymentsRepository;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsFactory;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsPaymentCompletedResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageConsumer {

    private final TossPaymentsRepository tossPaymentsRepository;
    private final ApplicationEventPublisher publisher;

    public MessageConsumer(TossPaymentsRepository tossPaymentsRepository,
        ApplicationEventPublisher publisher) {
        this.tossPaymentsRepository = tossPaymentsRepository;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "${rabbitmq.queueName}")
    @Transactional
    public void consumeApprovalResponse(TossPaymentsPaymentCompletedResponse response) {
        TossPayments entity = TossPaymentsFactory.create(response);
        tossPaymentsRepository.save(entity);
        entity.pollAllEvents().forEach(publisher::publishEvent);
    }
}
