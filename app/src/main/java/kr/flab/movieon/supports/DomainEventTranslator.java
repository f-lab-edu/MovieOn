package kr.flab.movieon.supports;

import kr.flab.movieon.account.domain.event.RegisteredAccountConfirmEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Receives all domain events and Convert to other module's domain events.
 */
@Component
class DomainEventTranslator {

    private final ApplicationEventPublisher publisher;

    DomainEventTranslator(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void translate(RegisteredAccountConfirmEvent event) {
        publisher.publishEvent(
            new kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent(
                event.getAccountId(), event.getEmail(),
                event.getEmailValidationToken(), event.occurredOn()
            )
        );
    }
}
