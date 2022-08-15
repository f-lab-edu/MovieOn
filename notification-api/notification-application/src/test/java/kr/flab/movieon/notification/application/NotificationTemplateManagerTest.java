package kr.flab.movieon.notification.application;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.UUID;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.notification.application.NotificationTemplateManager.TemplateCommand;
import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class NotificationTemplateManagerTest {

    @Test
    @DisplayName("템플릿 타입이 잘못되었을 경우 에러가 발생합니다.")
    void name() {
        // Arrange
        var arg = UUID.randomUUID().toString();
        var sut = new NotificationTemplateManager(new DummyNotificationTemplateRepository());

        // Act & Assert
        assertThatExceptionOfType(InvalidArgumentException.class)
            .isThrownBy(() -> sut.create(new TemplateCommand(arg, arg, arg, arg)));
    }

    private static final class DummyNotificationTemplateRepository implements
        NotificationTemplateRepository {

        @Override
        public NotificationTemplate save(
            NotificationTemplate entity) {
            return null;
        }

        @Override
        public NotificationTemplate findByTemplate(String typeName, String templateName) {
            return null;
        }
    }
}
