package kr.flab.movieon.order.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class OrderValidatorTest {

    @Test
    @DisplayName("주문을 검사하여 주문 항목이 비어있는 경우 예외가 발생합니다.")
    void sut_validate_an_exception_will_occur_if_the_orderLineItems_isEmpty() {
        // Arrange
        var sut = new OrderValidator(new ItemRepositoryStub(), new DummyPointManager());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.validate(isEmptyProduct()))
            .withMessage("주문 항목이 비어 있습니다.");
    }

    @Test
    @DisplayName("주문을 검사하여 기본 상품 정보가 변경된 경우 예외가 발생합니다.")
    void sut_validate_an_exception_will_occur_if_the_item_information_is_changed() {
        // Arrange
        var sut = new OrderValidator(new ItemRepositoryStub(), new DummyPointManager());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.validate(isChangedItem()))
            .withMessage("기본 상품 정보가 변경되었습니다.");
    }

    @Test
    @DisplayName("주문을 검사하여 기본 상품 정보가 변경된 경우 예외가 발생합니다.")
    void sut_validate_an_exception_will_occur_if_the_item_option_information_is_changed() {
        // Arrange
        var sut = new OrderValidator(new ItemRepositoryStub(), new DummyPointManager());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.validate(isChangedItemOption()))
            .withMessage("상품 옵션 정보가 변경되었습니다.");
    }

    private Order isChangedItemOption() {
        return Order.create(new Customer(1L), "CARD", BigDecimal.ZERO,
            List.of(
                new OrderLineItem(1L, "보이스", BigDecimal.valueOf(10000),
                    List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                ),
                new OrderLineItem(2L, "스파이더맨 어웨이 홈", BigDecimal.valueOf(13000),
                    List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                ),
                new OrderLineItem(3L, "어벤져스", BigDecimal.valueOf(12000),
                    List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                )
            )
        );
    }

    private Order isChangedItem() {
        return Order.create(new Customer(1L), "CARD", BigDecimal.ZERO,
            List.of(
                new OrderLineItem(1L, "보이스", BigDecimal.valueOf(9000),
                    List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                ),
                new OrderLineItem(2L, "스파이더맨 어웨이 홈", BigDecimal.valueOf(13000),
                    List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                ),
                new OrderLineItem(3L, "어벤져스", BigDecimal.valueOf(12000),
                    List.of(new OrderItemOption("480P", BigDecimal.valueOf(1200)))
                )
            )
        );
    }

    private Order isEmptyProduct() {
        return Order.create(new Customer(1L), "CARD", BigDecimal.ZERO,
            Collections.emptyList());
    }

    private static final class ItemRepositoryStub implements ItemRepository {

        @Override
        public List<Item> findAllById(List<Long> productIds) {
            return List.of(
                new Item(1L, "보이스", BigDecimal.valueOf(10000),
                    List.of(new ItemOption("480P", BigDecimal.valueOf(2000)))),
                new Item(2L, "스파이더맨", BigDecimal.valueOf(13000),
                    List.of(new ItemOption("720P", BigDecimal.valueOf(1000)))),
                new Item(3L, "어벤져스", BigDecimal.valueOf(12000),
                    List.of(new ItemOption("480P", BigDecimal.valueOf(1500))))
            );
        }
    }

}
