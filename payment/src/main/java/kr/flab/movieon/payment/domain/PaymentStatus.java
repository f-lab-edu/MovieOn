package kr.flab.movieon.payment.domain;

public enum PaymentStatus {
    PENDING("결제대기중"),
    PAYED("결제완료"),
    CANCELLED("결제취소"),
    FAILED("결제실패"),
    INVALID("위변조검증실패");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }
}
