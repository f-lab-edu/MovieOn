package kr.flab.movieon.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import kr.flab.movieon.order.domain.Order.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentCompletedProcessorTest {

    @Test
    @DisplayName("결제 금액과 주문 금액이 다른 경우 주문의 상태가 취소됨으로 변경")
    void sut_payed_if_payment_amount_is_diff_from_order_amount_the_order_status_is_cancel() {
        // Arrange
        var sut = new PaymentCompletedProcessor(new OrderRepositoryStub());

        // Act
        Order order = sut.pay(new PaymentCompletedEvent(
            "testId", "보이스", BigDecimal.valueOf(12000), new Date()));

        // Assert
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(order.pollAllEvents()).isNotNull();
    }

    @Test
    @DisplayName("주문의 상태가 취소됨일 경우, 예외가 발생한다.")
    void sut_payed_if_order_status_is_already_canceled_return_exception() {
        // Arrange
        var sut = new PaymentCompletedProcessor(new OrderRepositoryStub());

        // Act
        Order order = sut.pay(new PaymentCompletedEvent(
            "testId", "보이스", BigDecimal.valueOf(13000), new Date()));

        // Assert
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(order.pollAllEvents()).isNotNull();
    }

    private static final class OrderRepositoryStub implements OrderRepository {

        @Override
        public Order save(Order entity) {
            return null;
        }

        @Override
        public Order findByOrderId(String orderId) {
            var order = Order.create(new Customer(UUID.randomUUID().toString()), "CARD",
                BigDecimal.ZERO,
                List.of(new OrderLineItem(1L, "보이스", BigDecimal.valueOf(13000),
                        List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                    )
                )
            );
            order.cancel();
            return order;
        }
    }
}
