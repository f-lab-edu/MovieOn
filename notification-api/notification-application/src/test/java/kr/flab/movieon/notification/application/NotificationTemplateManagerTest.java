package kr.flab.movieon.notification.application;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.notification.application.NotificationTemplateManager.CreateTemplateCommand;
import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateType;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

final class NotificationTemplateManagerTest {

    @ParameterizedTest
    @AutoSource
    @DisplayName("템플릿 타입이 잘못되었을 경우 에러가 발생합니다.")
    void name(String arg) {
        // Arrange
        var sut = new NotificationTemplateManager(new DummyNotificationTemplateRepository());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.create(new CreateTemplateCommand(arg, arg, arg, arg)));
    }

    private static final class DummyNotificationTemplateRepository implements
        NotificationTemplateRepository {

        @Override
        public NotificationTemplate save(
            NotificationTemplate entity) {
            return null;
        }

        @Override
        public NotificationTemplate findByTemplateType(
            NotificationTemplateType templateType) {
            return null;
        }

        @Override
        public NotificationTemplate findById(Long templateId) {
            return null;
        }
    }
}
