package kr.flab.movieon.notification.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public record PaymentCompletedEvent(String accountId, String productName, BigDecimal price,
                                    BigDecimal discount, Date occurredOn) implements DomainEvent {

    @Override
    public String toString() {
        return "PaymentCompletedEvent{" + "accountId='" + accountId + '\'' + ", productName='"
            + productName + '\'' + ", price=" + price + ", discount=" + discount + ", occurredOn="
            + occurredOn + '}';
    }
}
