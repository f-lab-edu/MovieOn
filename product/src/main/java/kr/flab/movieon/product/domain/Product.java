package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import kr.flab.movieon.common.Days;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Product {

    public enum ProductType {
        PURCHASE, RENTAL
    }

    private Long id;
    private MovieDisplay display;
    private BigDecimal price;
    private int purchaseCount;
    private Days availableDays;
    private ProductType productType;

    public Product(MovieDisplay display, BigDecimal price,
        Days availableDays, ProductType productType) {
        this.display = display;
        this.price = price;
        this.purchaseCount = 0;
        this.availableDays = availableDays;
        this.productType = productType;
    }

    void setId(Long id) {
        this.id = id;
    }

    public ProductType getType() {
        return this.productType;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return this.display.getTitle();
    }

    public void purchased() {
        this.purchaseCount++;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAvailableDays() {
        return availableDays.getValue();
    }

}
