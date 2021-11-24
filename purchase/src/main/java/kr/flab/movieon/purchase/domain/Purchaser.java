package kr.flab.movieon.purchase.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Purchaser {

    private final Long purchaserId;

    public Purchaser(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }
}
