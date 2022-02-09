package kr.flab.movieon.notification.application;

import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import lombok.Getter;
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
            command.getTypeName(), command.getTemplateName(),
            command.getTitle(), command.getContents());

        templateRepository.save(template);
    }

    @Transactional
    public void update(Long templateId, String title, String contents) {
        var template = templateRepository.findById(templateId);
        template.update(title, contents);
    }

    @Getter
    public static final class CreateTemplateCommand {

        private final String typeName;
        private final String templateName;
        private final String title;
        private final String contents;

        public CreateTemplateCommand(String typeName, String templateName, String title,
            String contents) {
            this.typeName = typeName;
            this.templateName = templateName;
            this.title = title;
            this.contents = contents;
        }
    }
}
