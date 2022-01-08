package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public final class OrderProduct {

    private Long id;
    private Long productId;
    private String title;
    private BigDecimal price;

    public OrderProduct(Long productId, String title, BigDecimal price) {
        this.productId = productId;
        this.title = title;
        this.price = price;
    }
}
