package kr.flab.movieon.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
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
        Order order = sut.payed(new PaymentCompletedEvent("testId", BigDecimal.valueOf(12000)));

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
            return Order.create(new Customer(1L), "CARD", BigDecimal.ZERO,
                List.of(new OrderProduct(1L, "보이스", BigDecimal.valueOf(13000))));
        }
    }
}
