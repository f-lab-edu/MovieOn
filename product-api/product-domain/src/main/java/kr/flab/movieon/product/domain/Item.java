package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Item {

    protected Item() {

    }

    public enum ItemType {
        RENTAL, PURCHASE
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String name;
    private Period availableDays;
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
        Period availableDays, BigDecimal basePrice,
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

    public Period getAvailableDays() {
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
