package kr.flab.movieon.supports;

import kr.flab.movieon.account.domain.RegisteredAccountEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Receives all domain events and Convert to other module's domain events.
 */
@Component
final class DomainEventTranslator {

    private final ApplicationEventPublisher publisher;

    DomainEventTranslator(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @TransactionalEventListener
    public void translate(RegisteredAccountEvent event) {
        publisher.publishEvent(
            new kr.flab.movieon.notification.domain.RegisteredAccountEvent(event.getAccountId(),
                event.getUsername(), event.getEmail(), event.getEmailCheckToken(),
                event.occurredOn()));
    }
}
