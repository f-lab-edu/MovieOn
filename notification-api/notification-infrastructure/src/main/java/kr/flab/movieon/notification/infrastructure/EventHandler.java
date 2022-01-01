package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessDelegator;
import kr.flab.movieon.notification.domain.NotificationRepository;
import kr.flab.movieon.notification.domain.NotificationSenderDelegator;
import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.RegisterCompletedEvent;
import kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class EventHandler {

    private final NotificationSettingRepository settingRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationSenderDelegator senderDelegator;
    private final ExternalEventNotificationProcessDelegator processDelegator;

    public EventHandler(
        NotificationSettingRepository settingRepository,
        NotificationRepository notificationRepository,
        NotificationSenderDelegator senderDelegator,
        ExternalEventNotificationProcessDelegator processDelegator) {
        this.settingRepository = settingRepository;
        this.notificationRepository = notificationRepository;
        this.senderDelegator = senderDelegator;
        this.processDelegator = processDelegator;
    }

    @EventListener
    public void handle(RegisteredAccountConfirmEvent event) {
        var notification = notificationRepository.save(processDelegator.process(event));
        senderDelegator.send(notification);
    }

    @EventListener
    public void handle(RegisterCompletedEvent event) {
        var setting = NotificationSetting.defaultSetting(event.getId());
        settingRepository.save(setting);
    }
}
