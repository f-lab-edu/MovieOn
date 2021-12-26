package kr.flab.movieon.notification.infrastructure;

import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationMessageType;
import kr.flab.movieon.notification.domain.NotificationRepository;
import kr.flab.movieon.notification.domain.NotificationSender;
import kr.flab.movieon.notification.domain.NotificationTemplateId;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationType;
import kr.flab.movieon.notification.domain.Receiver;
import kr.flab.movieon.notification.domain.RegisteredAccountConfirmEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class EventHandler {

    private final NotificationRepository notificationRepository;
    private final NotificationTemplateRepository templateRepository;
    private final NotificationSender notificationSender;

    public EventHandler(NotificationRepository notificationRepository,
        NotificationTemplateRepository templateRepository,
        NotificationSender notificationSender) {
        this.notificationRepository = notificationRepository;
        this.templateRepository = templateRepository;
        this.notificationSender = notificationSender;
    }

    @EventListener
    public void handle(RegisteredAccountConfirmEvent event) {
        var template = templateRepository.findById(
            new NotificationTemplateId(NotificationType.EMAIL,
                NotificationMessageType.EMAIL_CONFIRM));

        var message = "MovieOn 서비스를 사용하려면 링크를 클릭해주세요.";

        // 템플릿 엔진을 사용하여 조회한 템플릿 모델을 HTML 파일로 변환

        var notification = Notification.toEmail(
            new Receiver(event.getAccountId()), message,
            event.getEmail());
        notificationRepository.save(notification);

        // 메세지 발송(비동기)
        notificationSender.send(notification);
    }
}
