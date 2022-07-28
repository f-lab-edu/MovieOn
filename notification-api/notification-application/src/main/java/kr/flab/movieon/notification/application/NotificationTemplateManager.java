package kr.flab.movieon.notification.application;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationTemplateManager {

    private final NotificationTemplateRepository templateRepository;

    public NotificationTemplateManager(NotificationTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Transactional
    public void create(CreateTemplateCommand command) {
        var template = NotificationTemplate.create(
            command.typeName(), command.templateName(),
            command.title(), command.contents());

        templateRepository.save(template);
    }

    @Transactional
    public void update(Long templateId, String title, String contents) {
        var template = templateRepository.findById(templateId);
        template.update(title, contents);
    }

    public record CreateTemplateCommand(String typeName, String templateName, String title,
                                        String contents) {}
}
