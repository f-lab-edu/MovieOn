package kr.flab.movieon.product.domain;

import java.time.LocalDateTime;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String name;
    private String availableDevices;
    private String providedQuality;
    private boolean drm;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ItemOption> options = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    protected Item() {

    }

    public Item(Long productId, String name, String availableDevices, String providedQuality,
        boolean drm) {
        this.productId = productId;
        this.name = name;
        this.availableDevices = availableDevices;
        this.providedQuality = providedQuality;
        this.drm = drm;
    }

    public void addOption(ItemOption itemOption) {
        options.add(itemOption);
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

    public String getAvailableDevices() {
        return availableDevices;
    }

    public String getProvidedQuality() {
        return providedQuality;
    }

    public boolean isDrm() {
        return drm;
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
