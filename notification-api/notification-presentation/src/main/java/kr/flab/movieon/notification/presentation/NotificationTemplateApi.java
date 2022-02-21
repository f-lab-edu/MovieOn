package kr.flab.movieon.notification.presentation;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import kr.flab.movieon.notification.application.NotificationTemplateManager;
import kr.flab.movieon.notification.application.NotificationTemplateManager.CreateTemplateCommand;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/notifications/templates",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class NotificationTemplateApi {

    private final NotificationTemplateManager templateManager;

    public NotificationTemplateApi(NotificationTemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(@RequestBody @Valid CreateTemplateRequest request) {
        templateManager.create(request.toCommand());
    }

    @PutMapping("/{templateId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@PathVariable Long templateId,
        @RequestBody @Valid UpdateTemplateRequest request) {
        templateManager.update(templateId, request.getTitle(), request.getContents());
    }

    public static final class CreateTemplateRequest {

        @NotEmpty
        private String typeName;
        @NotEmpty
        private String templateName;
        private String title;
        @NotEmpty
        private String contents;

        public CreateTemplateCommand toCommand() {
            return new CreateTemplateCommand(this.typeName, this.templateName, this.title,
                this.contents);
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        @Override
        public String toString() {
            return "CreateTemplateRequest{" + "typeName='" + typeName + '\'' + ", templateName='"
                + templateName + '\'' + ", title='" + title + '\'' + ", contents='" + contents
                + '\'' + '}';
        }
    }

    public static final class UpdateTemplateRequest {

        private String title;
        @NotEmpty
        private String contents;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        @Override
        public String toString() {
            return "UpdateTemplateRequest{" + "title='" + title + '\'' + ", contents='" + contents
                + '\'' + '}';
        }
    }
}
