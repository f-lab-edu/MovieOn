package kr.flab.movieon.order.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import kr.flab.movieon.order.application.command.CreateOrderCommand;
import kr.flab.movieon.order.application.command.CreateOrderCommand.CreateOrderItemOptionCommand;
import kr.flab.movieon.order.application.command.CreateOrderCommand.CreateOrderLineItemCommand;
import kr.flab.movieon.order.domain.Item;
import kr.flab.movieon.order.domain.ItemOption;
import kr.flab.movieon.order.domain.ItemRepository;
import kr.flab.movieon.order.domain.Order;
import kr.flab.movieon.order.domain.Order.OrderStatus;
import kr.flab.movieon.order.domain.OrderRepository;
import kr.flab.movieon.order.domain.OrderValidator;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

final class CreateOrderCommandHandlerTest {

    @ParameterizedTest
    @AutoSource
    @DisplayName("주문 생성 명령을 처리하고 이벤트가 등록된다.")
    void sut_create_order_command_handle(String accountId) {
        // Arrange
        var sut = new CreateOrderCommandHandler(new DummyOrderRepository(),
            new OrderValidator(new ItemRepositoryStub(), new DummyPointManager()));

        // Act
        var order = sut.create(accountId, createOrderCommand());

        // Assert
        assertThat(order.getTotalAmount()).isEqualTo(BigDecimal.valueOf(16000));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(order.pollAllEvents().size()).isEqualTo(1);
    }

    private CreateOrderCommand createOrderCommand() {
        return new CreateOrderCommand("CARD", BigDecimal.valueOf(2000),
            List.of(new CreateOrderLineItemCommand(1L, "보이스", BigDecimal.valueOf(10000),
                    List.of(new CreateOrderItemOptionCommand("480P", BigDecimal.valueOf(2000)))
                ),
                new CreateOrderLineItemCommand(2L, "스파이더맨", BigDecimal.valueOf(13000),
                    List.of(new CreateOrderItemOptionCommand("480P", BigDecimal.valueOf(3000)))
                )
            ));
    }

    private static final class DummyOrderRepository implements OrderRepository {

        @Override
        public Order save(Order entity) {
            return null;
        }

        @Override
        public Order findByOrderId(String orderId) {
            return null;
        }
    }

    private static final class ItemRepositoryStub implements ItemRepository {

        @Override
        public List<Item> findAllById(List<Long> itemIds) {
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
