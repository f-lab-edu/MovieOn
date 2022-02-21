package kr.flab.movieon.payment.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.flab.movieon.payment.infrastructure.jpa.TossPaymentsRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
