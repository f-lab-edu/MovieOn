package kr.flab.movieon.notification.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EMAIL")
public class EmailNotification extends Notification {

    private String email;
    private String title;

    protected EmailNotification() {

    }

    public EmailNotification(Receiver receiver, String message, String email,
        String title) {
        super(receiver, message);
        this.email = email;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }
}
