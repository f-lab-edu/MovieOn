package kr.flab.movieon.notification.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class PaymentCompletedEvent implements DomainEvent {

    private final String accountId;
    private final String productName;
    private final BigDecimal price;
    private final BigDecimal discount;
    private final Date occurredOn;

    public PaymentCompletedEvent(String accountId, String productName, BigDecimal price,
        BigDecimal discount, Date occurredOn) {
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

    public String getAccountId() {
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

    @Override
    public String toString() {
        return "PaymentCompletedEvent{" + "accountId='" + accountId + '\'' + ", productName='"
            + productName + '\'' + ", price=" + price + ", discount=" + discount + ", occurredOn="
            + occurredOn + '}';
    }
}
