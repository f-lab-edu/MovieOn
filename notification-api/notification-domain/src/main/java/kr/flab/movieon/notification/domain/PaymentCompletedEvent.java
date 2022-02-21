package kr.flab.movieon.notification.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class PaymentCompletedEvent implements DomainEvent {

    private final Long accountId;
    private final String productName;
    private final BigDecimal price;
    private final BigDecimal discount;
    private final Date occurredOn;

    public PaymentCompletedEvent(Long accountId, String productName,
        BigDecimal price, BigDecimal discount, Date occurredOn) {
        this.accountId = accountId;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.occurredOn = occurredOn;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
