package kr.flab.movieon.notification.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;

@Getter
public final class NotificationOption {

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private boolean disabled;

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
