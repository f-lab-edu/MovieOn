package kr.flab.movieon.notification.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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

    public NotificationType getType() {
        return type;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
