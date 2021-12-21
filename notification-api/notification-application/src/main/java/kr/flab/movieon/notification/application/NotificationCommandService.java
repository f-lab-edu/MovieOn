package kr.flab.movieon.notification.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationSender;

public final class NotificationCommandService {

    private final List<NotificationSender> senders;

    private Map<Class<? extends Notification>, NotificationSender>
        senderMap = new HashMap<>();

    public NotificationCommandService(List<NotificationSender> senders) {
        this.senders = senders;
        setSenderMap();
    }

    private void setSenderMap() {
        for (NotificationSender sender : this.senders) {
            senderMap.put(sender.appliesTo(), sender);
        }
    }

    public void send(Notification notification) {
        senderMap.get(notification.getClass())
            .send(notification);
    }
}
