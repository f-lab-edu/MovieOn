package kr.flab.movieon.payment.domain;

import lombok.Getter;

@Getter
public final class PaymentRedirectUri {

    private final String uri;

    public PaymentRedirectUri(String uri) {
        this.uri = uri;
    }
}
