package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessDelegator;
import kr.flab.movieon.notification.domain.NotificationRepository;
import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.RegisterCompletedEvent;
import kr.flab.movieon.notification.domain.RegisteredAccountEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public final class NotificationEventHandler {

    private final NotificationSettingRepository settingRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationSenderDelegator senderDelegator;
    private final ExternalEventNotificationProcessDelegator processDelegator;
    private final TransactionTemplate transactionTemplate;

    public NotificationEventHandler(
        NotificationSettingRepository settingRepository,
        NotificationRepository notificationRepository,
        NotificationSenderDelegator senderDelegator,
        ExternalEventNotificationProcessDelegator processDelegator,
        TransactionTemplate transactionTemplate) {
        this.settingRepository = settingRepository;
        this.notificationRepository = notificationRepository;
        this.senderDelegator = senderDelegator;
        this.processDelegator = processDelegator;
        this.transactionTemplate = transactionTemplate;
    }

    @EventListener
    public void handle(RegisteredAccountEvent event) {
        var notification = transactionTemplate.execute(
            status -> notificationRepository.save(processDelegator.process(event)));
        senderDelegator.send(notification);
    }

    @EventListener
    public void handle(RegisterCompletedEvent event) {
        var setting = NotificationSetting.defaultSetting(event.accountId());
        settingRepository.save(setting);
    }
}
