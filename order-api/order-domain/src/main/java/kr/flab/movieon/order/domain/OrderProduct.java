package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public final class OrderProduct {

    private Long id;
    private Long productId;
    private String name;
    private BigDecimal price;

    public OrderProduct(Long productId, String name, BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("주문 상품에 대한 가격이 잘못되었습니다.");
        }
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}
