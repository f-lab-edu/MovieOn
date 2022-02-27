package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Item {

    private final Long id;
    private final String productName;
    private final BigDecimal basePrice;
    private final List<ItemOption> itemOptions;

    public Item(Long id, String productName, BigDecimal basePrice,
        List<ItemOption> itemOptions) {
        this.id = id;
        this.productName = productName;
        this.basePrice = basePrice;
        this.itemOptions = itemOptions;
    }

    public boolean isSatisfiedBy(String productName, BigDecimal basePrice) {
        return this.productName.equals(productName) && this.basePrice.equals(basePrice);
    }

    public boolean isSatisfiedBy(List<OrderItemOption> options) {
        return !satisfied(options).isEmpty();
    }

    private List<OrderItemOption> satisfied(List<OrderItemOption> options) {
        return this.itemOptions
            .stream()
            .flatMap(itemOption -> options.stream().filter(itemOption::isSatisfiedBy))
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
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
