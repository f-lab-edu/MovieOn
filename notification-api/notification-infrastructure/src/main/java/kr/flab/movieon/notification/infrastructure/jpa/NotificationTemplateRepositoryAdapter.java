package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class NotificationTemplateRepositoryAdapter implements NotificationTemplateRepository {

    private final JpaNotificationTemplateRepository templateRepository;

    public NotificationTemplateRepositoryAdapter(
        JpaNotificationTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public NotificationTemplate save(NotificationTemplate entity) {
        return templateRepository.save(entity);
    }

    @Cacheable(value = "template", cacheManager = "caffeineCacheManager")
    @Override
    public NotificationTemplate findByTemplateType(NotificationTemplateType templateType) {
        return templateRepository.findByTemplateType(templateType);
    }

    @Override
    public NotificationTemplate findById(Long templateId) {
        return templateRepository.findById(templateId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
