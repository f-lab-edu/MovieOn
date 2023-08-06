package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "NOTIFICATION_TEMPLATES")
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

    public Long getId() {
        return id;
    }

    public NotificationTemplateType getTemplateType() {
        return templateType;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationTemplate that = (NotificationTemplate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
