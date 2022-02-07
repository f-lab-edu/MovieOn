package kr.flab.movieon.product.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public class Category {

    private Long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
