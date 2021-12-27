package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.NotificationSender;

public final class EmailNotificationSender implements NotificationSender<EmailNotification> {

    @Override
    public Class<EmailNotification> appliesTo() {
        return EmailNotification.class;
    }

    @Override
    public void send(EmailNotification notification) {

    }
}
