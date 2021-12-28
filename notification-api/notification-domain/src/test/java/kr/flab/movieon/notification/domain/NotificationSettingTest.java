package kr.flab.movieon.notification.domain;

import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.MARKETING;
import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.PURCHASE_INFO;
import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.USER_INFO;
import static kr.flab.movieon.notification.domain.NotificationType.EMAIL;
import static kr.flab.movieon.notification.domain.NotificationType.PUSH;
import static kr.flab.movieon.notification.domain.NotificationType.SMS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotificationSettingTest {

    @Test
    @DisplayName("사용자는 알림 설정 내에 존재하지 않는 알림 그룹은 비활성화 할 수 없다.")
    void sut_not_possible_deactivate_a_notificationGroup_notFound_within_notificationSetting() {
        // Arrange
        var sut = defaultSetting();

        // Act & Assert
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sut.disableGroup(MARKETING));
    }

    @Test
    @DisplayName("사용자는 알림 설정에서 구매 내역 알림 그룹을 비활성화한다.")
    void sut_user_has_disable_notificationGroup_in_notificationSetting() {
        // Arrange
        var sut = defaultSetting();

        // Act
        sut.disableGroup(PURCHASE_INFO);

        // Assert
        assertThat(sut.isDisabledGroup(PURCHASE_INFO)).isTrue();
    }

    @Test
    @DisplayName("사용자는 구매 내역 알림 그룹 내에 있는 이메일 알림 옵션을 비활성화한다.")
    void sut_user_has_disable_notificationOption_in_notificationGroup() {
        // Arrange
        var sut = defaultSetting();

        // Act
        sut.disableOptionInGroup(EMAIL, PURCHASE_INFO);

        // Assert
        assertThat(sut.isDisabledOptionInGroup(EMAIL, PURCHASE_INFO)).isTrue();
    }

    @Test
    @DisplayName("사용자는 비활성화된 알림 그룹 내에 있는 알림 옵션을 비활성화할 수 없습니다.")
    void sut_error_the_notificationOption_withIn_the_disable_notificationGroup_is_disabled() {
        // Arrange
        var sut = defaultSetting();

        // Act
        sut.disableGroup(PURCHASE_INFO);

        // Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.disableOptionInGroup(EMAIL, PURCHASE_INFO));
    }

    private NotificationSetting defaultSetting() {
        var notificationGroups = Set.of(
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
        return new NotificationSetting(new Receiver(1L), notificationGroups);
    }
}
