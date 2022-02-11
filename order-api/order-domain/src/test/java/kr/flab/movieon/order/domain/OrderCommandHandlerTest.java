package kr.flab.movieon.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kr.flab.movieon.order.domain.Order.OrderStatus;
import kr.flab.movieon.order.domain.commands.CreateOrder;
import kr.flab.movieon.order.domain.commands.CreateOrder.CreateOrderProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class OrderCommandHandlerTest {

    @Test
    @DisplayName("주문 생성 명령을 처리하고 이벤트가 발행된다.")
    void sut_create_order_command_handle() {
        // Arrange
        var sut = new OrderCommandHandler(new FakeOrderRepository(),
            new OrderValidator(new ProductRepositoryStub(), new DummyPointManager()));

        // Act
        var order = sut.create(1L, createOrderCommand());

        // Assert
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(order.pollAllEvents().size()).isEqualTo(1);
    }

    private CreateOrder createOrderCommand() {
        return new CreateOrder("CARD", BigDecimal.valueOf(2000),
            List.of(new CreateOrderProduct(1L, "보이스", BigDecimal.valueOf(10000)),
                new CreateOrderProduct(2L, "스파이더맨", BigDecimal.valueOf(13000))));
    }

    private static final class ProductRepositoryStub implements ProductRepository {

        @Override
        public List<Product> findAllById(List<Long> productIds) {
            return List.of(
                new Product(1L, "보이스", BigDecimal.valueOf(10000)),
                new Product(2L, "스파이더맨", BigDecimal.valueOf(13000)),
                new Product(3L, "어벤져스", BigDecimal.valueOf(12000))
            );
        }
    }
}
