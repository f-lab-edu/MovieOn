package kr.flab.movieon.notification.domain;

import kr.flab.movieon.common.domain.model.DomainEvent;

public interface ExternalEventNotificationProcessor<T extends DomainEvent> {

    Class<T> appliesTo();

    Notification process(T event);
}
