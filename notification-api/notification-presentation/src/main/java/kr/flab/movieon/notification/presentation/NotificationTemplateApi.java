package kr.flab.movieon.notification.presentation;

import kr.flab.movieon.notification.application.NotificationTemplateManager;
import kr.flab.movieon.notification.presentation.request.CreateTemplateRequest;
import kr.flab.movieon.notification.presentation.request.UpdateTemplateRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationTemplateApi implements NotificationTemplateSpecification {

    private final NotificationTemplateManager templateManager;

    public NotificationTemplateApi(NotificationTemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @Override
    public void create(CreateTemplateRequest request) {
        templateManager.create(request.toCommand());
    }

    @Override
    public void update(UpdateTemplateRequest request) {
        templateManager.update(request.toCommand());
    }

}
