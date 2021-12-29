package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.NotificationSetting;
import kr.flab.movieon.notification.domain.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {

    NotificationSetting findByReceiver(Receiver receiver);
}
