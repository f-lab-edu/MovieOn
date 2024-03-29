package kr.flab.movieon.product.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ITEMS")
public class Item {

    protected Item() {

    }

    public enum ItemType {
        RENTAL, PURCHASE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String name;
    private Integer availableDays;
    private BigDecimal basePrice;
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ItemOption> options = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public Item(Long productId, String name,
                Integer availableDays, BigDecimal basePrice,
                ItemType type, Set<ItemOption> options) {
        this.productId = productId;
        this.name = name;
        this.availableDays = availableDays;
        this.basePrice = basePrice;
        this.type = type;
        this.options = options;
    }

    public void addOption(ItemOption option) {
        this.options.add(option);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public ItemType getType() {
        return type;
    }

    public Set<ItemOption> getOptions() {
        return options;
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
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
