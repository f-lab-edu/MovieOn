package kr.flab.movieon.notification.domain;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Embeddable
@Getter
public final class Receiver {

    private Long accountId;

    public Receiver(Long accountId) {
        this.accountId = accountId;
    }
}
