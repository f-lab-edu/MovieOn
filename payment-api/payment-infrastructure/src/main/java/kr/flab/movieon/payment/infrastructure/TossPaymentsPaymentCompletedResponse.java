package kr.flab.movieon.payment.infrastructure;

import java.time.LocalDateTime;

public final class TossPaymentsPaymentCompletedResponse {

    private String version;
    private String orderId;
    private String orderName;
    private String paymentKey;
    private String type;
    private String method;
    private String mid;
    private String currency;
    private boolean useEscrow;
    private boolean cultureExpense;
    private String secret;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private String transactionKey;
    private Integer vat;
    private Integer taxFreeAmount;
    private TossPaymentsCardInfoResponse card;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isUseEscrow() {
        return useEscrow;
    }

    public void setUseEscrow(boolean useEscrow) {
        this.useEscrow = useEscrow;
    }

    public boolean isCultureExpense() {
        return cultureExpense;
    }

    public void setCultureExpense(boolean cultureExpense) {
        this.cultureExpense = cultureExpense;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getSuppliedAmount() {
        return suppliedAmount;
    }

    public void setSuppliedAmount(Integer suppliedAmount) {
        this.suppliedAmount = suppliedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Integer getTaxFreeAmount() {
        return taxFreeAmount;
    }

    public void setTaxFreeAmount(Integer taxFreeAmount) {
        this.taxFreeAmount = taxFreeAmount;
    }

    public TossPaymentsCardInfoResponse getCard() {
        return card;
    }

    public void setCard(TossPaymentsCardInfoResponse card) {
        this.card = card;
    }
}
