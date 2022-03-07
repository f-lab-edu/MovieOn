package kr.flab.movieon.notification.application;

import kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.NotificationType;
import kr.flab.movieon.notification.domain.Receiver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationSettingManager {

    private final NotificationSettingRepository settingRepository;

    public NotificationSettingManager(NotificationSettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public void disable(String accountId, String groupName) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.disableGroup(NotificationGroupType.findByGroup(groupName));
    }

    public void disable(String accountId, String groupName, String typeName) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.disableOptionInGroup(NotificationGroupType.findByGroup(groupName),
            NotificationType.findByType(typeName));
    }

    public void enable(String accountId, String groupName) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.enableGroup(NotificationGroupType.findByGroup(groupName));
    }

    public void enable(String accountId, String groupName, String typeName) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.enableOptionInGroup(NotificationGroupType.findByGroup(groupName),
            NotificationType.findByType(typeName));
    }
}
