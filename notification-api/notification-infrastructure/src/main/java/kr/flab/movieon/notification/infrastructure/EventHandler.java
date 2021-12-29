package kr.flab.movieon.notification.infrastructure;

import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.PURCHASE_INFO;
import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.USER_INFO;
import static kr.flab.movieon.notification.domain.NotificationType.EMAIL;
import static kr.flab.movieon.notification.domain.NotificationType.PUSH;
import static kr.flab.movieon.notification.domain.NotificationType.SMS;

import java.util.Set;
import kr.flab.movieon.notification.domain.NotificationGroup;
import kr.flab.movieon.notification.domain.NotificationMessageType;
import kr.flab.movieon.notification.domain.NotificationOption;
import kr.flab.movieon.notification.domain.NotificationRepository;
import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateId;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationType;
import kr.flab.movieon.notification.domain.Receiver;
import kr.flab.movieon.notification.domain.RegisterCompletedEvent;
import kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class EventHandler {

    private final NotificationRepository notificationRepository;
    private final NotificationTemplateRepository templateRepository;
    private final NotificationSettingRepository settingRepository;

    public EventHandler(NotificationRepository notificationRepository,
        NotificationTemplateRepository templateRepository,
        NotificationSettingRepository settingRepository) {
        this.notificationRepository = notificationRepository;
        this.templateRepository = templateRepository;
        this.settingRepository = settingRepository;
    }

    @EventListener
    public void handle(RegisteredAccountConfirmEvent event) {
        var template = templateRepository.findById(
            new NotificationTemplateId(NotificationType.EMAIL,
                NotificationMessageType.EMAIL_CONFIRM));

        var message = "MovieOn 서비스를 사용하려면 링크를 클릭해주세요.";

        // 템플릿 엔진을 사용하여 조회한 템플릿 모델을 HTML 파일로 변환

        // 메세지 발송(비동기)
    }

    @EventListener
    public void handle(RegisterCompletedEvent event) {
        var notificationGroups = defaultNotificationGroups();
        var setting = new NotificationSetting(new Receiver(event.getId()), notificationGroups);
        settingRepository.save(setting);
    }

    private Set<NotificationGroup> defaultNotificationGroups() {
        return Set.of(
            new NotificationGroup(PURCHASE_INFO, Set.of(
                new NotificationOption(EMAIL),
                new NotificationOption(SMS),
                new NotificationOption(PUSH)
            )),
            new NotificationGroup(USER_INFO, Set.of(
                new NotificationOption(EMAIL),
                new NotificationOption(SMS),
                new NotificationOption(PUSH)
            ))
        );
    }
}
