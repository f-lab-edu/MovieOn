package kr.flab.movieon.notification.presentation;

import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.notification.application.NotificationSettingManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/notifications")
final class NotificationSettingApi implements NotificationSettingSpecification {

    private final NotificationSettingManager settingManager;

    public NotificationSettingApi(NotificationSettingManager settingManager) {
        this.settingManager = settingManager;
    }

    @Override
    public void enable(String groupName, AuthenticatedUser user) {
        settingManager.enable(user.id(), groupName);
    }

    @Override
    public void enable(String groupName, String typeName, AuthenticatedUser user) {
        settingManager.enable(user.id(), groupName, typeName);
    }

    @Override
    public void disable(String groupName, AuthenticatedUser user) {
        settingManager.disable(user.id(), groupName);
    }

    @Override
    public void disable(String groupName, String typeName, AuthenticatedUser user) {
        settingManager.disable(user.id(), groupName, typeName);
    }
}
