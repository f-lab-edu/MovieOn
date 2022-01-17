package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.time.Period;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Product {

    public enum ProductType {
        PURCHASE, RENTAL
    }

    private Long id;
    private Category category;
    private MovieDisplay display;
    private BigDecimal price;
    private int purchaseCount;
    private Period availableDays;
    private ProductType productType;

    public Product(Category category,
        MovieDisplay display, BigDecimal price,
        Period availableDays, ProductType productType) {
        this.category = category;
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

    public Period getAvailableDays() {
        return availableDays;
    }

}
