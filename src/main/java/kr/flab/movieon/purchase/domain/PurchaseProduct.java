package kr.flab.movieon.purchase.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class PurchaseProduct {

    private Long productId;
    private BigDecimal price;
    private boolean available;
    private LocalDateTime purchased;
    private LocalDateTime expired;

    private PurchaseProduct(Long productId, BigDecimal price, boolean available) {
        this.productId = productId;
        this.price = price;
        this.available = available;
    }

    public static PurchaseProduct create(Long productId, BigDecimal price) {
        return new PurchaseProduct(productId, price, false);
    }

    public void purchased(int availableDays) {
        this.available = true;
        this.purchased = LocalDateTime.now();
        this.expired = this.purchased.plusDays(availableDays);
    }

    public void expired() {
        this.available = false;
    }
}
