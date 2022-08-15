package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.common.domain.model.DomainEvent;

public record PaymentCompletedEvent(String orderId, String orderName, BigDecimal payedAmount,
                                    Date occurredOn) implements DomainEvent {

}
