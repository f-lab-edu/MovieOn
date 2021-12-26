package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateId;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import org.springframework.stereotype.Component;

@Component
public final class NotificationTemplateRepositoryAdapter implements NotificationTemplateRepository {

    private final JpaNotificationTemplateRepository templateRepository;

    public NotificationTemplateRepositoryAdapter(
        JpaNotificationTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public NotificationTemplate save(NotificationTemplate entity) {
        return templateRepository.save(entity);
    }

    @Override
    public NotificationTemplate findById(NotificationTemplateId templateId) {
        return templateRepository.findById(templateId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
