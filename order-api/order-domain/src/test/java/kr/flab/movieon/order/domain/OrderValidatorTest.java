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
    void sut_validate_an_exception_will_occur_if_the_orderProducts_isEmpty() {
        // Arrange
        var sut = new OrderValidator(new ProductRepositoryStub(), new DummyPointManager());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.validate(isEmptyProduct()));
    }

    @Test
    @DisplayName("주문을 검사하여 상품 정보가 변경된 경우 예외가 발생합니다.")
    void sut_validate_an_exception_will_occur_if_the_product_information_is_changed() {
        // Arrange
        var sut = new OrderValidator(new ProductRepositoryStub(), new DummyPointManager());

        // Act & Assert
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> sut.validate(isChangedProduct()));
    }

    private Order isChangedProduct() {
        return Order.create(new Customer(1L), "CARD", BigDecimal.ZERO,
            List.of(
                new OrderProduct(1L, "보이스", BigDecimal.valueOf(9000)),
                new OrderProduct(2L, "스파이더맨 어웨이 홈", BigDecimal.valueOf(13000)),
                new OrderProduct(3L, "어벤져스", BigDecimal.valueOf(12000))
            ));
    }

    private Order isEmptyProduct() {
        return Order.create(new Customer(1L), "CARD", BigDecimal.ZERO,
            Collections.emptyList());
    }

    private static final class DummyPointManager implements PointManager {

        @Override
        public void canUseOfPoint(Long customerId, BigDecimal useOfPoint) {

        }
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
