package kr.flab.movieon.notification.infrastructure;

import java.util.List;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessDelegator;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessor;
import kr.flab.movieon.notification.domain.NotificationSender;
import kr.flab.movieon.notification.domain.NotificationSenderDelegator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationModuleConfiguration {

    @Bean
    public ExternalEventNotificationProcessDelegator externalEventNotificationProcessorDelegator(
        List<ExternalEventNotificationProcessor> processors) {
        return new ExternalEventNotificationProcessDelegator(processors);
    }

    @Bean
    public NotificationSenderDelegator notificationSenderDelegator(
        List<NotificationSender> senders) {
        return new NotificationSenderDelegator(senders);
    }
}
