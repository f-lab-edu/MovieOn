package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationTemplateRepository extends
    JpaRepository<NotificationTemplate, Long> {

    NotificationTemplate findByTemplateType(NotificationTemplateType templateType);
}
