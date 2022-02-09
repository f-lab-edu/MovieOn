package kr.flab.movieon.product.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@EqualsAndHashCode
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

    @Builder
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

}
