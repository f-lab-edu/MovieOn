package kr.flab.movieon.order.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Customer {

    @Column(nullable = false)
    private Long accountId;

    protected Customer() {

    }

    public Customer(Long accountId) {
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
        Customer customer = (Customer) o;
        return Objects.equals(accountId, customer.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
