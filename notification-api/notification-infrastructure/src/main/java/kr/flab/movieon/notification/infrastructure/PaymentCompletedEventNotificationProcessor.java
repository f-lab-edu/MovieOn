package kr.flab.movieon.notification.infrastructure;

import static kr.flab.movieon.notification.domain.NotificationType.EMAIL;

import java.util.HashMap;
import java.util.Map;
import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessor;
import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateProcessor;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateType;
import kr.flab.movieon.notification.domain.PaymentCompletedEvent;
import kr.flab.movieon.notification.domain.Receiver;
import kr.flab.movieon.notification.integrate.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class PaymentCompletedEventNotificationProcessor
    implements ExternalEventNotificationProcessor<PaymentCompletedEvent> {

    private final NotificationTemplateRepository templateRepository;
    private final NotificationTemplateProcessor notificationTemplateProcessor;
    private final NotificationSettingRepository settingRepository;
    private final AccountRepository accountRepository;
    private final String host;

    public PaymentCompletedEventNotificationProcessor(
        NotificationTemplateRepository templateRepository,
        NotificationTemplateProcessor notificationTemplateProcessor,
        NotificationSettingRepository settingRepository,
        AccountRepository accountRepository,
        @Value("${app.mail.host}") String host) {
        this.templateRepository = templateRepository;
        this.notificationTemplateProcessor = notificationTemplateProcessor;
        this.settingRepository = settingRepository;
        this.accountRepository = accountRepository;
        this.host = host;
    }

    @Override
    public Class<PaymentCompletedEvent> appliesTo() {
        return PaymentCompletedEvent.class;
    }

    @Override
    public Notification process(PaymentCompletedEvent event) {
        var setting = settingRepository.findByReceiver(new Receiver(event.getAccountId()));
        if (setting.isDisabledOptionInGroup(NotificationGroupType.PAYMENT_INFO, EMAIL)) {
            throw new IllegalStateException("?????? ?????? ????????? ???????????? ?????? ??????????????????.");
        }

        var account = accountRepository.findById(event.getAccountId());

        var template = templateRepository
            .findByTemplateType(new NotificationTemplateType(EMAIL, "?????? ?????? ??????"));

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", account.getUsername());
        variables.put("message", "??????????????? " + event.occurredOn() + " MovieOn?????? ????????? ????????? ????????? ????????????.");
        variables.put("productName", event.getProductName());
        variables.put("price", event.getPrice());
        variables.put("discount", event.getDiscount());
        variables.put("host", host);

        var message = notificationTemplateProcessor.process(template.getContents(), variables);
        return new EmailNotification(new Receiver(event.getAccountId()), message,
            account.getEmail(), template.getTitle());
    }
}
