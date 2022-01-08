package kr.flab.movieon.order.domain;

import lombok.Getter;

@Getter
public final class Customer {

    private Long accountId;

    public Customer(Long accountId) {
        this.accountId = accountId;
    }
}
