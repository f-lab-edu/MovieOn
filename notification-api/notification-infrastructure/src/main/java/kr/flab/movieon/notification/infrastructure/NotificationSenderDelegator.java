package kr.flab.movieon.notification.infrastructure;

import java.util.HashMap;
import java.util.Map;
import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationSender;
import kr.flab.movieon.notification.domain.NotificationType;

public final class NotificationSenderDelegator implements NotificationSender {

    private static final Map<NotificationType, NotificationSender> senderMap
        = new HashMap<>();

    static {
        senderMap.put(NotificationType.EMAIL, new EmailNotificationSender());
    }

    @Override
    public void send(Notification notification) {
        senderMap.get(notification.getType()).send(notification);
    }
}
