package kr.flab.movieon.notification.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import kr.flab.movieon.notification.domain.FakeNotificationSettingRepository;
import kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType;
import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class NotificationSettingManagerTest {

    @Test
    @DisplayName("사용자 알림 설정 내에 회원 정보 알림 그룹을 비활성화한다.")
    void sut_disable_the_notificationGroup_in_the_notificationSetting() {
        // Arrange
        var settingRepository = new FakeNotificationSettingRepository();
        settingRepository.save(NotificationSetting.defaultSetting(1L));
        var sut = new NotificationSettingManager(settingRepository);

        // Act
        Throwable result = catchThrowable(
            () -> sut.disable(1L, "USER_INFO"));

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("사용자의 회원 정보 알림 그룹 내에서 이메일 알림 옵션을 비활성화한다.")
    void sut_disable_the_notificationOption_in_the_notificationGroup() {
        // Arrange
        var settingRepository = new FakeNotificationSettingRepository();
        settingRepository.save(NotificationSetting.defaultSetting(1L));
        var sut = new NotificationSettingManager(settingRepository);

        // Act
        Throwable result = catchThrowable(
            () -> sut.disable(1L, "USER_INFO", "EMAIL"));

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("비활성화된 회원 정보 알림 그룹을 다시 활성화한다.")
    void sut_enable_the_notificationGroup_in_the_notificationSetting() {
        // Arrange
        var settingRepository = new FakeNotificationSettingRepository();
        var setting = NotificationSetting.defaultSetting(1L);
        setting.disableGroup(NotificationGroupType.USER_INFO);
        settingRepository.save(setting);
        var sut = new NotificationSettingManager(settingRepository);

        // Act
        Throwable result = catchThrowable(
            () -> sut.enable(1L, "USER_INFO"));

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("회원 정보 알림 그룹 내에 있는 비활성화된 이메일 알림 옵션을 활성화한다.")
    void sut_enable_the_notificationOption_in_the_notificationGroup() {
        // Arrange
        var settingRepository = new FakeNotificationSettingRepository();
        var setting = NotificationSetting.defaultSetting(1L);
        setting.disableOptionInGroup(NotificationGroupType.USER_INFO, NotificationType.EMAIL);
        settingRepository.save(setting);
        var sut = new NotificationSettingManager(settingRepository);

        // Act
        Throwable result = catchThrowable(
            () -> sut.enable(1L, "USER_INFO", "EMAIL"));

        // Assert
        assertThat(result).isNull();
    }
}
