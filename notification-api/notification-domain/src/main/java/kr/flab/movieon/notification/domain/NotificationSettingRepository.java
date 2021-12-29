package kr.flab.movieon.notification.domain;

public interface NotificationSettingRepository {

    NotificationSetting save(NotificationSetting entity);

    NotificationSetting findByReceiver(Receiver receiver);
}
