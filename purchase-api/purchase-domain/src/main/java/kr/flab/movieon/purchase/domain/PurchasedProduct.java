package kr.flab.movieon.purchase.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

public final class PurchasedProduct {

    private Long productId;
    private String title;
    private BigDecimal purchasedPrice;
    private Period availableDays;
    private boolean available;
    private LocalDateTime purchased;
    private LocalDateTime expired;

    private PurchasedProduct(Long productId, String title, BigDecimal purchasedPrice,
        boolean available, Period availableDays) {
        this.productId = productId;
        this.title = title;
        this.purchasedPrice = purchasedPrice;
        this.available = available;
        this.availableDays = availableDays;
    }

    public static PurchasedProduct create(Long productId, String title, BigDecimal price,
        Period availableDays) {
        return new PurchasedProduct(productId, title, price, false,
            availableDays);
    }

    public void complete() {
        this.available = true;
        this.purchased = LocalDateTime.now();
        this.expired = this.purchased.plusDays(availableDays.getDays());
    }

    public void expire() {
        this.available = false;
    }

    public BigDecimal getPurchasedPrice() {
        return this.purchasedPrice;
    }

    public String getTitle() {
        return title;
    }
}
