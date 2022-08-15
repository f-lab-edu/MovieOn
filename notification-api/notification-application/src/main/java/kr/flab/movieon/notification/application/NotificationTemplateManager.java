package kr.flab.movieon.notification.application;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationTemplateManager {

    private final NotificationTemplateRepository templateRepository;

    public NotificationTemplateManager(NotificationTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional
    public void create(TemplateCommand command) {
        var template = NotificationTemplate.create(
            command.typeName(), command.templateName(),
            command.title(), command.contents());

        templateRepository.save(template);
    }

    @Transactional
    @CacheEvict(value = "template", cacheManager = "caffeineCacheManager",
        key = "#command.typeName().concat(':').concat(#command.templateName())")
    public void update(TemplateCommand command) {
        var template = templateRepository.findByTemplate(command.typeName, command.templateName);
        template.update(command.title, command.contents);
    }

    public record TemplateCommand(String typeName, String templateName, String title,
                                  String contents) {

    }
}
