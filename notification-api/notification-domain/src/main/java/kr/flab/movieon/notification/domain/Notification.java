package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Receiver receiver;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createdAt;
    private String email;
    private String tel;

    protected Notification() {}

    private Notification(Receiver receiver, String message,
        NotificationType type, LocalDateTime createdAt, String email, String tel) {
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.email = email;
        this.tel = tel;
    }
}
