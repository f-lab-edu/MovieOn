package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;

public abstract class Notification {

    private Long id;
    private Receiver receiver;
    private String message;
    private LocalDateTime createdAt;

    public Notification(Receiver receiver, String message, LocalDateTime createdAt) {
        this.receiver = receiver;
        this.message = message;
        this.createdAt = createdAt;
    }
}
