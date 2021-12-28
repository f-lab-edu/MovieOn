package kr.flab.movieon.account.application;

import kr.flab.movieon.account.domain.RegisteredAccountConfirmEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Receives all domain events from account module.
 * and Convert to other module's domain events.
 */
@Component
class AccountEventTranslator {

    private final ApplicationEventPublisher publisher;

    AccountEventTranslator(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void translate(RegisteredAccountConfirmEvent event) {
        publisher.publishEvent(
            new kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent(
                event.getAccountId(), event.getEmail(), event.getEmailCheckToken()
            )
        );
    }
}
