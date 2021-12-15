package kr.flab.movieon.notification.domain;

import java.time.LocalDateTime;

public final class SmsNotification extends Notification {

    private String tel;

    public SmsNotification(Receiver receiver, String message, LocalDateTime createdAt,
        String tel) {
        super(receiver, message, createdAt);
        this.tel = tel;
    }
}
