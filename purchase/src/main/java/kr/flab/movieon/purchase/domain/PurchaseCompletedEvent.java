package kr.flab.movieon.purchase.domain;

import java.util.Date;
import kr.flab.movieon.common.DomainEvent;

public final class PurchaseCompletedEvent implements DomainEvent {

    private final Long purchaseId;
    private final Date occurredOn;

    public PurchaseCompletedEvent(Purchase purchase) {
        this.purchaseId = purchase.getId();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return null;
    }
}
