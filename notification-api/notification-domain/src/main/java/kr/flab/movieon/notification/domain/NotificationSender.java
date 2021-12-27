package kr.flab.movieon.notification.domain;

public interface NotificationSender<T extends Notification> {

    Class<T> appliesTo();

    void send(T notification);
}
