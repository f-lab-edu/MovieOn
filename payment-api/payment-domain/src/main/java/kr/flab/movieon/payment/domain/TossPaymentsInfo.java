package kr.flab.movieon.payment.domain;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;

@Embeddable
public class TossPaymentsInfo {

    protected TossPaymentsInfo() {

    }

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

    public TossPaymentsInfo(String version, String orderId, String orderName,
        String paymentKey, String type, String mid, Integer totalAmount,
        Integer balanceAmount, Integer suppliedAmount, String status,
        LocalDateTime requestedAt, LocalDateTime approvedAt, String transactionKey,
        Integer vat, Integer taxFreeAmount) {
        this.version = version;
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentKey = paymentKey;
        this.type = type;
        this.mid = mid;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.suppliedAmount = suppliedAmount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.transactionKey = transactionKey;
        this.vat = vat;
        this.taxFreeAmount = taxFreeAmount;
    }

    public String getVersion() {
        return version;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public String getType() {
        return type;
    }

    public String getMid() {
        return mid;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public Integer getSuppliedAmount() {
        return suppliedAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public Integer getVat() {
        return vat;
    }

    public Integer getTaxFreeAmount() {
        return taxFreeAmount;
    }
}
