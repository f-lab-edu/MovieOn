package kr.flab.movieon.notification.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.Set;
import kr.flab.movieon.common.error.InvalidArgumentException;

@Entity
@Table(name = "NOTIFICATION_GROUPS")
public class NotificationGroup {

    public enum NotificationGroupType {
        PURCHASE_INFO, MARKETING, USER_INFO, PAYMENT_INFO;

        public static NotificationGroupType findByGroup(String groupName) {
            return Arrays.stream(values())
                    .filter(g -> g.name().equals(groupName))
                    .findFirst()
                    .orElseThrow(() -> new InvalidArgumentException("일치하는 group을 찾을 수 없습니다."));
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationGroupType type;
    private boolean disabled;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<NotificationOption> options;

    protected NotificationGroup() {
    }

    public NotificationGroup(NotificationGroupType type,
                             Set<NotificationOption> options) {
        this.type = type;
        this.options = options;
    }

    public boolean isEqualTo(NotificationGroupType type) {
        return this.type.equals(type);
    }

    public void enable() {
        this.disabled = false;
    }

    public void enableOption(NotificationType notificationType) {
        var option = options.stream()
                .filter(o -> o.isEqualTo(notificationType))
                .findFirst()
                .orElseThrow(InvalidArgumentException::new);
        option.enable();
    }

    public void disable() {
        this.disabled = true;
    }

    public void disableOption(NotificationType notificationType) {
        var option = options.stream()
                .filter(o -> o.isEqualTo(notificationType))
                .findFirst()
                .orElseThrow(InvalidArgumentException::new);
        option.disable();
    }

    public boolean isDisabledOption(NotificationType notificationType) {
        return options.stream()
                .anyMatch(o -> o.isEqualTo(notificationType) && o.isDisabled());
    }

    public Long getId() {
        return id;
    }

    public NotificationGroupType getType() {
        return type;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public Set<NotificationOption> getOptions() {
        return options;
    }
}
