package kr.flab.movieon.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import kr.flab.movieon.order.application.OrderCommandService.CreateOrderCommand;
import kr.flab.movieon.order.application.OrderCommandService.CreateOrderCommand.CreateOrderProduct;
import kr.flab.movieon.order.domain.FakeOrderRepository;
import kr.flab.movieon.order.domain.Order.OrderStatus;
import kr.flab.movieon.order.domain.OrderCreatedEvent;
import kr.flab.movieon.order.domain.OrderValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
final class SpecsForCreateOrder {

    @Test
    @DisplayName("주문 생성 명령에 따라 주문을 생성하고 주문 생성됨 이벤트가 발행된다.")
    void sut_create_order_command_and_published_order_created_event() {
        // Arrange
        var publisher = mock(ApplicationEventPublisher.class);
        var sut = new OrderCommandService(new FakeOrderRepository(), mock(OrderValidator.class),
            publisher);

        // Act
        var order = sut.create(1L, getCommand());

        // Assert
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
        verify(publisher, times(1))
            .publishEvent(Mockito.any(OrderCreatedEvent.class));
    }

    private CreateOrderCommand getCommand() {
        return new CreateOrderCommand("CARD", BigDecimal.valueOf(2000),
            List.of(new CreateOrderProduct(1L, "보이스", BigDecimal.valueOf(9500)),
                new CreateOrderProduct(2L, "스파이더맨", BigDecimal.valueOf(13000))));
    }
}
