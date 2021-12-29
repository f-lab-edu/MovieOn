package kr.flab.movieon.notification.domain;

import lombok.Getter;

@Getter
public final class RegisterCompletedEvent {

    private final Long id;

    public RegisterCompletedEvent(Long id) {
        this.id = id;
    }
}
