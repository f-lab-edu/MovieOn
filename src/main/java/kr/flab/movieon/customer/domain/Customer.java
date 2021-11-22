package kr.flab.movieon.customer.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Customer {

    private Long id;
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setId(Long id) {
        this.id = id;
    }
}
