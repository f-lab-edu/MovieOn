package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.Notification;
import kr.flab.movieon.notification.domain.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public final class NotificationRepositoryAdapter implements NotificationRepository {

    private final JpaNotificationRepository notificationRepository;

    public NotificationRepositoryAdapter(JpaNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification save(Notification entity) {
        return notificationRepository.save(entity);
    }
}
