package kr.flab.movieon.notification.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class NotificationTemplateId implements Serializable {

    @Column(name = "type")
    private NotificationType type;
    @Column(name = "message_type")
    private NotificationMessageType messageType;

    public NotificationTemplateId(NotificationType type,
        NotificationMessageType messageType) {
        this.type = type;
        this.messageType = messageType;
    }
}
