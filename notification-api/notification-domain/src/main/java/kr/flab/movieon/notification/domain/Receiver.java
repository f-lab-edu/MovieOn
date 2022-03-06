package kr.flab.movieon.notification.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Receiver {

    @Column(nullable = false)
    private Long accountId;

    protected Receiver() {

    }

    public Receiver(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Receiver receiver = (Receiver) o;
        return Objects.equals(accountId, receiver.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
