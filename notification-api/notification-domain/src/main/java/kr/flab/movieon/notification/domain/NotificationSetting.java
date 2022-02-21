package kr.flab.movieon.notification.domain;

import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.PAYMENT_INFO;
import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.PURCHASE_INFO;
import static kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType.USER_INFO;
import static kr.flab.movieon.notification.domain.NotificationType.EMAIL;
import static kr.flab.movieon.notification.domain.NotificationType.PUSH;
import static kr.flab.movieon.notification.domain.NotificationType.SMS;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import kr.flab.movieon.notification.domain.NotificationGroup.NotificationGroupType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class NotificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Receiver receiver;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<NotificationGroup> groups;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    protected NotificationSetting() {

    }

    private NotificationSetting(Receiver receiver, Set<NotificationGroup> groups) {
        this.receiver = receiver;
        this.groups = groups;
    }

    void setId(Long id) {
        this.id = id;
    }

    public void disableGroup(NotificationGroupType groupType) {
        var group = groups.stream()
            .filter(g -> g.isEqualTo(groupType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
        group.disable();
    }

    public boolean isDisabledGroup(NotificationGroupType groupType) {
        return groups.stream()
            .anyMatch(g -> g.isEqualTo(groupType) && g.isDisabled());
    }

    public void disableOptionInGroup(NotificationGroupType groupType,
        NotificationType notificationType) {
        var group = groups.stream()
            .filter(g -> g.isEqualTo(groupType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
        if (group.isDisabled()) {
            throw new IllegalStateException("해당 알림 그룹이 비활성화되어 있습니다.");
        }
        group.disableOption(notificationType);
    }

    public boolean isDisabledOptionInGroup(NotificationGroupType groupType,
        NotificationType notificationType) {
        return groups.stream()
            .anyMatch(g -> g.isEqualTo(groupType) && !g.isDisabled()
                && g.isDisabledOption(notificationType));
    }

    public void enableGroup(NotificationGroupType groupType) {
        var group = groups.stream()
            .filter(g -> g.isEqualTo(groupType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
        group.enable();
    }

    public void enableOptionInGroup(NotificationGroupType groupType,
        NotificationType notificationType) {
        var group = groups.stream()
            .filter(g -> g.isEqualTo(groupType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
        if (group.isDisabled()) {
            throw new IllegalStateException("해당 알림 그룹이 비활성화되어 있습니다.");
        }
        group.enableOption(notificationType);
    }

    public static NotificationSetting defaultSetting(Long accountId) {
        return new NotificationSetting(new Receiver(accountId), Set.of(
            new NotificationGroup(PURCHASE_INFO, Set.of(
                new NotificationOption(EMAIL),
                new NotificationOption(SMS),
                new NotificationOption(PUSH)
            )),
            new NotificationGroup(USER_INFO, Set.of(
                new NotificationOption(EMAIL),
                new NotificationOption(SMS),
                new NotificationOption(PUSH)
            )),
            new NotificationGroup(PAYMENT_INFO, Set.of(
                new NotificationOption(EMAIL),
                new NotificationOption(SMS),
                new NotificationOption(PUSH)
            ))
        ));
    }

    public Long getId() {
        return id;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Set<NotificationGroup> getGroups() {
        return groups;
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
        NotificationSetting that = (NotificationSetting) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
