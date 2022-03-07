package kr.flab.movieon.notification.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Receiver {

    @Column(nullable = false)
    private String accountId;

    protected Receiver() {

    }

    public Receiver(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
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
