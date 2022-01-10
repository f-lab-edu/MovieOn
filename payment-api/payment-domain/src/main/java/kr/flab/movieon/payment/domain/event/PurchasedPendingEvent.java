package kr.flab.movieon.payment.domain.event;

import java.util.Date;
import kr.flab.movieon.common.DomainEvent;
import lombok.Getter;

@Getter
public final class PurchasedPendingEvent implements DomainEvent {

    private final Long purchaseId;
    private final String paymentMethod;
    private final Date occurredOn;

    public PurchasedPendingEvent(Long purchaseId, String paymentMethod, Date occurredOn) {
        this.purchaseId = purchaseId;
        this.paymentMethod = paymentMethod;
        this.occurredOn = occurredOn;
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
