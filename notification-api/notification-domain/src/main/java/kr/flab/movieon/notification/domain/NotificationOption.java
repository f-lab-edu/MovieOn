package kr.flab.movieon.notification.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class NotificationOption {

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private boolean disabled;

    protected NotificationOption() {

    }

    public NotificationOption(NotificationType type) {
        this.type = type;
    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }

    public boolean isEqualTo(NotificationType type) {
        return this.type.equals(type);
    }
}
