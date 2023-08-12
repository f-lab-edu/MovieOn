package kr.flab.movieon.product.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    protected Product() {

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String thumbnails;
    @ManyToOne
    private Category category;
    private ProductContentsDetail contentsDetail;

    public Product(String name, String description, String thumbnails,
        Category category, ProductContentsDetail contentsDetail) {
        this.name = name;
        this.description = description;
        this.thumbnails = thumbnails;
        this.category = category;
        this.contentsDetail = contentsDetail;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public Category getCategory() {
        return category;
    }

    public ProductContentsDetail getContentsDetail() {
        return contentsDetail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
