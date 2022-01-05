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

    public void disable(Long accountId, NotificationGroupType groupType) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.disableGroup(groupType);
    }

    public void disable(Long accountId, NotificationGroupType groupType, NotificationType type) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.disableOptionInGroup(groupType, type);
    }

    public void enable(Long accountId, NotificationGroupType groupType) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.enableGroup(groupType);
    }

    public void enable(Long accountId, NotificationGroupType groupType, NotificationType type) {
        var setting = settingRepository.findByReceiver(new Receiver(accountId));
        setting.enableOptionInGroup(groupType, type);
    }
}
