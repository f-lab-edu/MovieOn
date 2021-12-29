package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.NotificationSettingRepository;
import kr.flab.movieon.notification.domain.Receiver;
import org.springframework.stereotype.Component;

@Component
public final class NotificationSettingRepositoryAdapter implements NotificationSettingRepository {

    private final JpaNotificationSettingRepository settingRepository;

    public NotificationSettingRepositoryAdapter(
        JpaNotificationSettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public NotificationSetting save(NotificationSetting entity) {
        return settingRepository.save(entity);
    }

    @Override
    public NotificationSetting findByReceiver(Receiver receiver) {
        return settingRepository.findByReceiver(receiver);
    }
}
