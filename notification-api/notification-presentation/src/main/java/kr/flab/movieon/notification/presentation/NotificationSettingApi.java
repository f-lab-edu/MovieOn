package kr.flab.movieon.notification.presentation;

import kr.flab.movieon.common.AccountAuthentication;
import kr.flab.movieon.notification.application.NotificationSettingManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/notifications")
final class NotificationSettingApi {

    private final NotificationSettingManager settingManager;

    public NotificationSettingApi(NotificationSettingManager settingManager) {
        this.settingManager = settingManager;
    }

    @PatchMapping("/{groupName}/enable")
    public void enable(@PathVariable String groupName,
        @AuthenticationPrincipal AccountAuthentication authentication) {
        settingManager.enable(authentication.getAccountId(), groupName);
    }

    @PatchMapping("/{groupName}/enable/{typeName}")
    public void enable(@PathVariable String groupName, @PathVariable String typeName,
        @AuthenticationPrincipal AccountAuthentication authentication) {
        settingManager.enable(authentication.getAccountId(), groupName, typeName);
    }

    @PatchMapping("/{groupName}/disable")
    public void disable(@PathVariable String groupName,
        @AuthenticationPrincipal AccountAuthentication authentication) {
        settingManager.disable(authentication.getAccountId(), groupName);
    }

    @PatchMapping("/{groupName}/disable/{typeName}")
    public void disable(@PathVariable String groupName, @PathVariable String typeName,
        @AuthenticationPrincipal AccountAuthentication authentication) {
        settingManager.disable(authentication.getAccountId(), groupName, typeName);
    }
}
