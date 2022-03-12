package kr.flab.movieon.notification.domain;

import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.MARKETING;
import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.PURCHASE_INFO;
import static kr.flab.movieon.notification.domain.NotificationType.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.common.error.InvalidArgumentException;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

class NotificationSettingTest {

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 비활성화된 구매 내역 알림 그룹을 활성화한다.")
    void sut_user_has_already_disabled_notificationGroup_enable_in_notificationSetting(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);
        sut.disableGroup(PURCHASE_INFO);

        // Act
        sut.enableGroup(PURCHASE_INFO);

        // Assert
        assertThat(sut.isDisabledGroup(PURCHASE_INFO)).isFalse();
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 구매 내역 알림 그룹 내에 비활성화된 이메일 수신 옵션을 활성화한다.")
    void sut_user_has_already_disabled_notificationOption_enable_in_notificationGroup(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);
        sut.disableOptionInGroup(PURCHASE_INFO, EMAIL);

        // Act
        sut.enableOptionInGroup(PURCHASE_INFO, EMAIL);

        // Assert
        assertThat(sut.isDisabledOptionInGroup(PURCHASE_INFO, EMAIL)).isFalse();
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 비활성화된 알림 그룹 내에 있는 알림 옵션을 활성화할 수 없습니다.")
    void sut_error_the_notificationOption_withIn_the_enable_notificationGroup_is_disabled(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);

        // Act
        sut.disableGroup(PURCHASE_INFO);

        // Assert
        assertThatExceptionOfType(IsDisabledNotificationGroupException.class)
            .isThrownBy(() -> sut.enableOptionInGroup(PURCHASE_INFO, EMAIL));
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 알림 설정 내에 존재하지 않는 알림 그룹은 비활성화 할 수 없다.")
    void sut_not_possible_deactivate_a_notificationGroup_notFound_within_notificationSetting(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);

        // Act & Assert
        assertThatExceptionOfType(InvalidArgumentException.class)
            .isThrownBy(() -> sut.disableGroup(MARKETING));
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 알림 설정에서 구매 내역 알림 그룹을 비활성화한다.")
    void sut_user_has_disable_notificationGroup_in_notificationSetting(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);

        // Act
        sut.disableGroup(PURCHASE_INFO);

        // Assert
        assertThat(sut.isDisabledGroup(PURCHASE_INFO)).isTrue();
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 구매 내역 알림 그룹 내에 있는 이메일 알림 옵션을 비활성화한다.")
    void sut_user_has_disable_notificationOption_in_notificationGroup(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);

        // Act
        sut.disableOptionInGroup(PURCHASE_INFO, EMAIL);

        // Assert
        assertThat(sut.isDisabledOptionInGroup(PURCHASE_INFO, EMAIL)).isTrue();
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("사용자는 비활성화된 알림 그룹 내에 있는 알림 옵션을 비활성화할 수 없습니다.")
    void sut_error_the_notificationOption_withIn_the_disable_notificationGroup_is_disabled(
        String accountId
    ) {
        // Arrange
        var sut = NotificationSetting.defaultSetting(accountId);

        // Act
        sut.disableGroup(PURCHASE_INFO);

        // Assert
        assertThatExceptionOfType(IsDisabledNotificationGroupException.class)
            .isThrownBy(() -> sut.disableOptionInGroup(PURCHASE_INFO, EMAIL));
    }
}
