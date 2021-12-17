package kr.flab.movieon.notification.infrastructure;

import java.time.LocalDateTime;
import kr.flab.movieon.notification.application.NotificationCommandService;
import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.NotificationRepository;
import kr.flab.movieon.notification.domain.Receiver;
import kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent;

public final class EventHandler {

    private final NotificationRepository notificationRepository;
    private final NotificationCommandService notificationCommandService;

    public EventHandler(
        NotificationRepository notificationRepository,
        NotificationCommandService notificationCommandService) {
        this.notificationRepository = notificationRepository;
        this.notificationCommandService = notificationCommandService;
    }

    public void handle(RegisteredAccountConfirmEvent event) {
        var notification = new EmailNotification(
            new Receiver(event.getAccountId()), "",
            LocalDateTime.now(),
            event.getEmail());

        notificationRepository.save(notification);

        notificationCommandService.send(notification);
    }
}
