package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
    private Period availableDays;
    private BigDecimal basePrice;
    private ItemType type;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ItemOption> options = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public Item(Long productId, Period availableDays, BigDecimal basePrice,
        ItemType type, Set<ItemOption> options) {
        this.productId = productId;
        this.availableDays = availableDays;
        this.basePrice = basePrice;
        this.type = type;
        this.options = options;
    }

    public void addOption(ItemOption option) {
        this.options.add(option);
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
