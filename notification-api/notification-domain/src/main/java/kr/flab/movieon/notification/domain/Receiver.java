package kr.flab.movieon.notification.domain;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Embeddable
@Getter
public class Receiver {

    private Long accountId;

    protected Receiver() {

    }

    public Receiver(Long accountId) {
        this.accountId = accountId;
    }
}
