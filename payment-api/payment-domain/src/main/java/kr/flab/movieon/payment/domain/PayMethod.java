package kr.flab.movieon.payment.domain;

import java.util.Arrays;

public enum PayMethod {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    ACCOUNT_TRANSFER("계좌이체");

    private final String description;

    PayMethod(String description) {
        this.description = description;
    }

    public static PayMethod findByMethod(String description) {
        return Arrays.stream(values())
            .filter(m -> m.description.equals(description))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
