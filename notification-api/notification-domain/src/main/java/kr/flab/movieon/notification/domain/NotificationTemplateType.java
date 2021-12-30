package kr.flab.movieon.notification.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public final class NotificationTemplateType {

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Column(unique = true, nullable = false)
    private String name;

    public NotificationTemplateType(NotificationType type, String name) {
        this.type = type;
        this.name = name;
    }
}
