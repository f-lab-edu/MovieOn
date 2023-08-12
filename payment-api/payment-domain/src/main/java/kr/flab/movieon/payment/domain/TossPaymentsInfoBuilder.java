package kr.flab.movieon.payment.domain;

import java.time.LocalDateTime;

public final class TossPaymentsInfoBuilder {

    private String version;
    private String orderId;
    private String orderName;
    private String paymentKey;
    private String type;
    private String mid;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private String transactionKey;
    private Integer vat;
    private Integer taxFreeAmount;

    private TossPaymentsInfoBuilder() {

    }

    public static TossPaymentsInfoBuilder builder() {
        return new TossPaymentsInfoBuilder();
    }

    public TossPaymentsInfoBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TossPaymentsInfoBuilder orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public TossPaymentsInfoBuilder orderName(String orderName) {
        this.orderName = orderName;
        return this;
    }

    public TossPaymentsInfoBuilder paymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
        return this;
    }

    public TossPaymentsInfoBuilder type(String type) {
        this.type = type;
        return this;
    }

    public TossPaymentsInfoBuilder mid(String mid) {
        this.mid = mid;
        return this;
    }

    public TossPaymentsInfoBuilder totalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public TossPaymentsInfoBuilder balanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public TossPaymentsInfoBuilder suppliedAmount(Integer suppliedAmount) {
        this.suppliedAmount = suppliedAmount;
        return this;
    }

    public TossPaymentsInfoBuilder status(String status) {
        this.status = status;
        return this;
    }

    public TossPaymentsInfoBuilder transactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
        return this;
    }

    public TossPaymentsInfoBuilder requestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
        return this;
    }

    public TossPaymentsInfoBuilder approvedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
        return this;
    }

    public TossPaymentsInfoBuilder vat(Integer vat) {
        this.vat = vat;
        return this;
    }

    public TossPaymentsInfoBuilder taxFreeAmount(Integer taxFreeAmount) {
        this.taxFreeAmount = taxFreeAmount;
        return this;
    }

    public TossPaymentsInfo build() {
        return new TossPaymentsInfo(version, orderId, orderName, paymentKey,
                type, mid, totalAmount, balanceAmount, suppliedAmount, status,
                requestedAt, approvedAt, transactionKey, vat, taxFreeAmount);
    }
}
