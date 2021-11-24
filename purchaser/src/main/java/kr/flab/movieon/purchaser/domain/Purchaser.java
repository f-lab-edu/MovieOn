package kr.flab.movieon.purchaser.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
public final class Purchaser {

    private Long id;
    private String name;

    public Purchaser(String name) {
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
