package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;

public final class EmailNotification extends Notification {

    private String email;

    public EmailNotification(Receiver receiver, String message, LocalDateTime createdAt,
        String email) {
        super(receiver, message, createdAt);
        this.email = email;
    }
}
