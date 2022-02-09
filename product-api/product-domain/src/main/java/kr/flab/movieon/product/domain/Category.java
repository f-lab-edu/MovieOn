package kr.flab.movieon.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }
}
