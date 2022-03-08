package kr.flab.movieon.supports;

import kr.flab.movieon.account.domain.RegisterCompletedEvent;
import kr.flab.movieon.account.domain.RegisteredAccountEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Receives all domain events and Convert to other module's domain events.
 */
@Component
final class DomainEventTranslator {

    private final ApplicationEventPublisher publisher;

    DomainEventTranslator(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void translate(RegisteredAccountEvent event) {
        publisher.publishEvent(
            new kr.flab.movieon.notification.domain.RegisteredAccountEvent(event.getAccountId(),
                event.getUsername(), event.getEmail(), event.getEmailCheckToken(),
                event.occurredOn()));
    }

    @EventListener
    public void translate(RegisterCompletedEvent event) {
        publisher.publishEvent(
            new kr.flab.movieon.notification.domain.RegisterCompletedEvent(event.getAccountId(),
                event.occurredOn()));
    }
}
