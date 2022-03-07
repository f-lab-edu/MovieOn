package kr.flab.movieon.notification.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationSender;
import org.springframework.scheduling.annotation.Async;

public class NotificationSenderDelegator {

    private final List<NotificationSender> senders;

    private Map<Class<? extends Notification>, NotificationSender> senderMap
        = new HashMap<>();

    public NotificationSenderDelegator(List<NotificationSender> senders) {
        this.senders = senders;
        setSenderMap(this.senders);
    }

    private void setSenderMap(List<NotificationSender> senders) {
        for (NotificationSender sender : senders) {
            senderMap.put(sender.appliesTo(), sender);
        }
    }

    @Async
    public void send(Notification notification) {
        senderMap.get(notification.getClass()).send(notification);
    }
}
