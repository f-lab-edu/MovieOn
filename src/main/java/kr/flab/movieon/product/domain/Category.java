package kr.flab.movieon.product.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Category {

    private Long id;
    private String name;
    private Long productId;

    public Category(String name, Long productId) {
        this.name = name;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getProductId() {
        return productId;
    }
}
