package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {

}
