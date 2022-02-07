package kr.flab.movieon.product.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Item {

    private Long id;
    private Long productId;
    private String name;
    private String availableDevices;
    private String providedQuality;

    private boolean drm;
    private List<String> images = new ArrayList<>();

    private Set<ItemOption> options = new HashSet<>();

    @Builder
    public Item(Long productId, String name, String availableDevices, String providedQuality,
        boolean drm, List<String> images) {
        this.productId = productId;
        this.name = name;
        this.availableDevices = availableDevices;
        this.providedQuality = providedQuality;
        this.drm = drm;
        this.images = images;
    }

    public void addOption(ItemOption itemOption) {
        options.add(itemOption);
    }

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
