package kr.flab.movieon.order.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.List;
import kr.flab.movieon.common.error.InvalidArgumentException;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

final class OrderTests {

    @ParameterizedTest
    @AutoSource
    @DisplayName("주문 상품에 대한 가격이 0원 미만일 경우 예외가 발생합니다.")
    void create_order_failed_order_product_price_is_less_than_zero(String accountId) {
        // Act & Assert
        assertThatExceptionOfType(InvalidArgumentException.class).isThrownBy(() ->
            Order.create(new Customer(accountId), "CARD",
                BigDecimal.valueOf(2000), List.of(
                    new OrderLineItem(1L, "보이스", BigDecimal.valueOf(-1),
                        List.of(new OrderItemOption("480P", BigDecimal.valueOf(2000)))))));
    }
}
