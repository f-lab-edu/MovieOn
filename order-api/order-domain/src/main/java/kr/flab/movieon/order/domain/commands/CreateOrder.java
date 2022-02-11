package kr.flab.movieon.order.domain.commands;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class CreateOrder {

    private final String payMethod;
    private final BigDecimal useOfPoint;
    private final List<CreateOrderProduct> products;

    @Builder
    public CreateOrder(String payMethod, BigDecimal useOfPoint,
        List<CreateOrderProduct> products) {
        this.payMethod = payMethod;
        this.useOfPoint = useOfPoint;
        this.products = products;
    }

    @Getter
    public static final class CreateOrderProduct {

        private final Long productId;
        private final String productName;
        private final BigDecimal price;

        public CreateOrderProduct(Long productId, String productName, BigDecimal price) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
        }
    }
}
