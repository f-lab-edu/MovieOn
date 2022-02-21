package kr.flab.movieon.payment.domain;

public enum PayMethod {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    ACCOUNT_TRANSFER("계좌이체");

    private final String description;

    PayMethod(String description) {
        this.description = description;
    }
}
