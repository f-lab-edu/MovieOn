package kr.flab.movieon.purchase.integrate;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class Product {

    private final String title;
    private final BigDecimal price;
    private final int availableDays;
    private final String type;

    @Builder
    public Product(String title, BigDecimal price, int availableDays, String type) {
        this.title = title;
        this.price = price;
        this.availableDays = availableDays;
        this.type = type;
    }
}
