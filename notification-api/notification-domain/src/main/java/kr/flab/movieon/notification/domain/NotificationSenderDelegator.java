package kr.flab.movieon.notification.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NotificationSenderDelegator {

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

    public void send(Notification notification) {
        senderMap.get(notification.getClass()).send(notification);
    }
}
