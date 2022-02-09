package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NotificationTemplateType templateType;

    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    protected NotificationTemplate() {

    }

    private NotificationTemplate(NotificationTemplateType templateType, String title,
        String contents) {
        this.templateType = templateType;
        this.title = title;
        this.contents = contents;
    }

    public static NotificationTemplate create(String typeName, String templateName,
        String title, String contents) {
        return new NotificationTemplate(new NotificationTemplateType(
            NotificationType.findByType(typeName), templateName), title, contents);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
