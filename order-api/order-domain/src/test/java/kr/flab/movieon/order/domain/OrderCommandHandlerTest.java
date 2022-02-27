package kr.flab.movieon.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import kr.flab.movieon.order.domain.Order.OrderStatus;
import kr.flab.movieon.order.domain.commands.CreateOrder;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderItemOption;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class OrderCommandHandlerTest {

    @Test
    @DisplayName("주문 생성 명령을 처리하고 이벤트가 발행된다.")
    void sut_create_order_command_handle() {
        // Arrange
        var sut = new OrderCommandHandler(new FakeOrderRepository(),
            new OrderValidator(new ItemRepositoryStub(), new DummyPointManager()));

        // Act
        var order = sut.create(1L, createOrderCommand());

        // Assert
        assertThat(order.getTotalAmount()).isEqualTo(BigDecimal.valueOf(16000));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(order.pollAllEvents().size()).isEqualTo(1);
    }

    private CreateOrder createOrderCommand() {
        return new CreateOrder("CARD", BigDecimal.valueOf(2000),
            List.of(new CreateOrderLineItem(1L, "보이스", BigDecimal.valueOf(10000),
                    List.of(new CreateOrderItemOption("480P", BigDecimal.valueOf(2000)))
                ),
                new CreateOrderLineItem(2L, "스파이더맨", BigDecimal.valueOf(13000),
                    List.of(new CreateOrderItemOption("480P", BigDecimal.valueOf(3000)))
                )
            ));
    }

    private static final class ItemRepositoryStub implements ItemRepository {

        @Override
        public List<Item> findAllById(List<Long> productIds) {
            return List.of(
                new Item(1L, "보이스", BigDecimal.valueOf(10000),
                    List.of(new ItemOption("480P", BigDecimal.valueOf(2000)))),
                new Item(2L, "스파이더맨", BigDecimal.valueOf(13000),
                    List.of(new ItemOption("480P", BigDecimal.valueOf(3000)))),
                new Item(3L, "어벤져스", BigDecimal.valueOf(12000), Collections.emptyList())
            );
        }
    }
}
