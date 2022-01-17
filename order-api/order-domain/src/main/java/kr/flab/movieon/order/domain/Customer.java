package kr.flab.movieon.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class Customer {

    private final Long accountId;

    public Customer(Long accountId) {
        this.accountId = accountId;
    }
}
