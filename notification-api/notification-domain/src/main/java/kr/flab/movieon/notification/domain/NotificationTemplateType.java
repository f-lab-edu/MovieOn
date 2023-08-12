package kr.flab.movieon.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class NotificationTemplateType {

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Column(nullable = false)
    private String name;

    protected NotificationTemplateType() {

    }

    public NotificationTemplateType(NotificationType type, String name) {
        this.type = type;
        this.name = name;
    }

    public NotificationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationTemplateType that = (NotificationTemplateType) o;
        return type == that.type && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }
}
