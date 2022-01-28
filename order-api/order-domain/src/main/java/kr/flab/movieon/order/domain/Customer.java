package kr.flab.movieon.order.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public final class Customer {

    @Column(nullable = false)
    private Long accountId;

    protected Customer() {

    }

    public Customer(Long accountId) {
        this.accountId = accountId;
    }
}
