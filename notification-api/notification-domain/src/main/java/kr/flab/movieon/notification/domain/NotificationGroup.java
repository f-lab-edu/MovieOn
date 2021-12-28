package kr.flab.movieon.notification.domain;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import lombok.Getter;

@Getter
public class NotificationGroup {

    public enum NotificationGroupType {
        PURCHASE_INFO, MARKETING, USER_INFO
    }

    @Enumerated(EnumType.STRING)
    private NotificationGroupType type;
    private boolean disabled;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<NotificationOption> options;

    public NotificationGroup(NotificationGroupType type,
        Set<NotificationOption> options) {
        this.type = type;
        this.options = options;
    }

    public boolean isEqualTo(NotificationGroupType type) {
        return this.type.equals(type);
    }

    public void disable() {
        this.disabled = true;
    }

    public void disableOption(NotificationType notificationType) {
        var option = options.stream()
            .filter(o -> o.isEqualTo(notificationType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
        option.disable();
    }

    public boolean isDisabledOption(NotificationType notificationType) {
        return options.stream()
            .anyMatch(o -> o.isEqualTo(notificationType) && o.isDisabled());
    }
}
