package kr.flab.movieon.payment.infrastructure;

import kr.flab.movieon.payment.infrastructure.jpa.TossPaymentsRepositoryAdapter;
import kr.flab.movieon.payment.infrastructure.toss.DefaultTossPaymentsPaymentProcessor;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsPaymentApprovalCommandVerifier;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsPaymentApprovalProcessor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Import({
    TossPaymentsRepositoryAdapter.class,
    DefaultTossPaymentsPaymentProcessor.class,
    TossPaymentsPaymentApprovalCommandVerifier.class,
    TossPaymentsPaymentApprovalProcessor.class
})
@Configuration
public class PaymentModuleConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("local")
    public Queue paymentQueue(@Value("${rabbitmq.queueName}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    @Profile("local")
    public TopicExchange topicExchange(@Value("${rabbitmq.exchange}") String exchange) {
        return new TopicExchange(exchange);
    }

    @Bean
    @Profile("local")
    public Binding binding(Queue queue, TopicExchange exchange,
        @Value("${rabbitmq.routingKey}") String routingKey) {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with(routingKey);
    }

    @Bean
    @Profile("local")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}
