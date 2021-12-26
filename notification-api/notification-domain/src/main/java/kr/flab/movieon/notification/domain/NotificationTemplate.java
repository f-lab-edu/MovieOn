package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
public class NotificationTemplate {

    @EmbeddedId
    private NotificationTemplateId templateId;

    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    protected NotificationTemplate() {

    }

    private NotificationTemplate(NotificationTemplateId templateId, String title,
        String contents) {
        this.templateId = templateId;
        this.title = title;
        this.contents = contents;
    }

    public static NotificationTemplate toEmail(NotificationTemplateId id,
        String title, String contents) {
        return new NotificationTemplate(id, title, contents);
    }
}
