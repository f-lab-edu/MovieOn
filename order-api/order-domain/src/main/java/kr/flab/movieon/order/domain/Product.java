package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class Product {

    private final Long id;
    private final String name;
    private final BigDecimal price;

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public boolean isSatisfiedBy(String name, BigDecimal price) {
        return this.name.equals(name) && this.price.equals(price);
    }
}
