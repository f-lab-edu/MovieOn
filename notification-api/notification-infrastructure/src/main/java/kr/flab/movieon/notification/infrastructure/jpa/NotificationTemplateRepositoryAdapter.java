package kr.flab.movieon.notification.infrastructure.jpa;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import kr.flab.movieon.notification.domain.NotificationTemplateType;
import kr.flab.movieon.notification.domain.NotificationType;
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

    @Cacheable(value = "template", cacheManager = "caffeineCacheManager",
        key = "#typeName.concat(':').concat(#templateName)")
    @Override
    public NotificationTemplate findByTemplate(String typeName, String templateName) {
        return templateRepository.findByTemplateType(new NotificationTemplateType(
            NotificationType.findByType(typeName), templateName));
    }
}
