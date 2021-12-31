package kr.flab.movieon.notification.infrastructure;

import static kr.flab.movieon.notification.domain.NotificationType.EMAIL;

import kr.flab.movieon.notification.domain.NotificationRepository;
import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateType;
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
        var template = templateRepository
            .findByTemplateType(new NotificationTemplateType(EMAIL, "계정 확인 메일"));

        var message = "MovieOn 서비스를 사용하려면 링크를 클릭해주세요.";

        // 템플릿 엔진을 사용하여 조회한 템플릿 모델을 HTML 파일로 변환

        // 메세지 발송(비동기)
    }

    @EventListener
    public void handle(RegisterCompletedEvent event) {
        var setting = NotificationSetting.defaultSetting(event.getId());
        settingRepository.save(setting);
    }
}
