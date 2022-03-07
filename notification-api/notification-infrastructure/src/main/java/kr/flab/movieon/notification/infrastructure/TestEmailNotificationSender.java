package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public final class TestEmailNotificationSender implements NotificationSender<EmailNotification> {

    private static final Logger log = LoggerFactory.getLogger(TestEmailNotificationSender.class);

    @Override
    public Class<EmailNotification> appliesTo() {
        return EmailNotification.class;
    }

    @Override
    public void send(EmailNotification notification) {
        log.debug("Email Notification Send Success!!");
    }
}
