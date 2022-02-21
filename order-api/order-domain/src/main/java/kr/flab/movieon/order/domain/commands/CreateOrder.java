package kr.flab.movieon.order.domain.commands;

import java.math.BigDecimal;
import java.util.List;

public final class CreateOrder {

    private final String payMethod;
    private final BigDecimal useOfPoint;
    private final List<CreateOrderProduct> products;

    public CreateOrder(String payMethod, BigDecimal useOfPoint,
        List<CreateOrderProduct> products) {
        this.payMethod = payMethod;
        this.useOfPoint = useOfPoint;
        this.products = products;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public BigDecimal getUseOfPoint() {
        return useOfPoint;
    }

    public List<CreateOrderProduct> getProducts() {
        return products;
    }

    public static final class CreateOrderProduct {

        private final Long productId;
        private final String productName;
        private final BigDecimal price;

        public CreateOrderProduct(Long productId, String productName, BigDecimal price) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
        }

        public Long getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
