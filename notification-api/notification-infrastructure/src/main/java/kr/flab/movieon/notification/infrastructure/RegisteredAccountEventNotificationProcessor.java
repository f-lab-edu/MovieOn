package kr.flab.movieon.notification.infrastructure;

import java.util.HashMap;
import java.util.Map;
import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessor;
import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationTemplateProcessor;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.Receiver;
import kr.flab.movieon.notification.domain.RegisteredAccountEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class RegisteredAccountEventNotificationProcessor
    implements ExternalEventNotificationProcessor<RegisteredAccountEvent> {

    private final NotificationTemplateRepository templateRepository;
    private final NotificationTemplateProcessor notificationTemplateProcessor;
    private final String host;

    public RegisteredAccountEventNotificationProcessor(
        NotificationTemplateRepository templateRepository,
        NotificationTemplateProcessor notificationTemplateProcessor,
        @Value("${app.mail.host}") String host) {
        this.templateRepository = templateRepository;
        this.notificationTemplateProcessor = notificationTemplateProcessor;
        this.host = host;
    }

    @Override
    public Class<RegisteredAccountEvent> appliesTo() {
        return RegisteredAccountEvent.class;
    }

    @Override
    public Notification process(RegisteredAccountEvent event) {
        var template = templateRepository
            .findByTemplate("EMAIL", "계정 확인 메일");

        Map<String, Object> variables = new HashMap<>();
        variables.put("link", "/api/v1/auth/confirm?token=" + event.emailCheckToken()
            + "&email=" + event.email());
        variables.put("username", event.username());
        variables.put("linkName", "이메일 인증하기");
        variables.put("message", "MovieOn 서비스를 사용하려면 링크를 클릭해주세요.");
        variables.put("host", host);

        var message = notificationTemplateProcessor.process(template.getContents(), variables);

        return new EmailNotification(new Receiver(event.accountId()),
            message, event.email(), template.getTitle());
    }
}
