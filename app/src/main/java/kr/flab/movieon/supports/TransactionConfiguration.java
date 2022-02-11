package kr.flab.movieon.supports;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TransactionConfiguration {

    @Bean
    public TransactionTemplate transactionTemplate(
        PlatformTransactionManager platformTransactionManager) {
        var template = new TransactionTemplate();
        template.setTransactionManager(platformTransactionManager);
        template.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        template.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
        return template;
    }
}
