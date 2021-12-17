package kr.flab.movieon.notification.domain;

public interface NotificationSender<T> {

    Class<T> appliesTo();

    void send(T notification);
}
