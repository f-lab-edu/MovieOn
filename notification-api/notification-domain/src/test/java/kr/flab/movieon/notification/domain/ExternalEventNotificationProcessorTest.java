package kr.flab.movieon.notification.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import kr.flab.movieon.common.domain.model.DomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExternalEventNotificationProcessorTest {

    @Test
    @DisplayName("외부 이벤트 알림 처리 위임자를 통해 이벤트를 처리하고 알림 개체를 반환합니다.")
    void sut_external_event_process_delegate_return_notification() {
        // Arrange
        var sut = new ExternalEventNotificationProcessDelegator(List.of(
            new DummyExternalEventNotificationProcessor()));

        // Act
        Notification notification = sut.process(new SampleDomainEvent());

        // Assert
        assertThat(notification).isNotNull();
    }

    private static final class DummyExternalEventNotificationProcessor implements
        ExternalEventNotificationProcessor<SampleDomainEvent> {

        @Override
        public Class<SampleDomainEvent> appliesTo() {
            return SampleDomainEvent.class;
        }

        @Override
        public Notification process(SampleDomainEvent event) {
            return new EmailNotification(new Receiver("accountId"),
                "Test", "test@gmail.com", "test mail");
        }
    }

    private static final class SampleDomainEvent implements DomainEvent {

        @Override
        public Date occurredOn() {
            return new Date();
        }
    }
}
