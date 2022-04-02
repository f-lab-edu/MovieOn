package kr.flab.movieon.order.infrastructure;

import kr.flab.movieon.order.domain.OrderValidator;
import kr.flab.movieon.order.domain.PaymentCompletedProcessor;
import kr.flab.movieon.order.infrastructure.jpa.OrderRepositoryAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
    OrderRepositoryAdapter.class,
    OrderValidator.class,
    PaymentCompletedProcessor.class
})
@Configuration
public class OrderModuleConfiguration {

}
