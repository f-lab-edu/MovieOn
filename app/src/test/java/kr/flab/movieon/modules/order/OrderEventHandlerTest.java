package kr.flab.movieon.modules.order;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import kr.flab.movieon.modules.IntegrateTestExtension;
import kr.flab.movieon.order.domain.OrderCanceledEvent;
import kr.flab.movieon.order.domain.OrderCompletedEvent;
import kr.flab.movieon.order.domain.PaymentCompletedEvent;
import kr.flab.movieon.order.infrastructure.OrderEventHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@RecordApplicationEvents
public class OrderEventHandlerTest extends IntegrateTestExtension {

    @Autowired private OrderEventHandler sut;
    @Autowired private ApplicationEvents recoredEvent;

    @Test
    @DisplayName("결제 완료 이벤트를 처리하는데, 데이터가 맞지 않는 경우 결제 취소 이벤트를 발행한다.")
    void name() {
        PaymentCompletedEvent event = new PaymentCompletedEvent(
            "ord_202203101620471034855694", "영화 2건 결제",
            BigDecimal.valueOf(20000), new Date()
        );

        sut.handle(event);

        assertThat(recoredEvent.stream(OrderCanceledEvent.class).count()).isEqualTo(1);
    }

    @Test
    @DisplayName("결제 완료 이벤트를 처리하는데, 데이터가 맞지 않는 경우 결제 취소 이벤트를 발행한다.")
    void name11() {
        PaymentCompletedEvent event = new PaymentCompletedEvent(
            "ord_202203101620471034855694", "영화 2건 결제",
            BigDecimal.valueOf(21700), new Date()
        );

        sut.handle(event);

        assertThat(recoredEvent.stream(OrderCompletedEvent.class).count()).isEqualTo(1);
    }
}
