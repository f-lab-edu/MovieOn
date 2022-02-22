package kr.flab.movieon.payment.domain;

import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public final class TossPaymentsPaymentApprovalCompleted implements DomainEvent {

    private final String orderId;
    private final String orderName;
    private final Integer payedAmount;
    private final Date occurredOn;

    public TossPaymentsPaymentApprovalCompleted(TossPayments tossPayments) {
        this.orderId = tossPayments.getInfo().getOrderId();
        this.orderName = tossPayments.getInfo().getOrderName();
        this.payedAmount = tossPayments.getInfo().getTotalAmount();
        this.occurredOn = new Date();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public Integer getPayedAmount() {
        return payedAmount;
    }
}
