package kr.flab.movieon.supports;

import org.springframework.context.ApplicationEventPublisher;
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
}
