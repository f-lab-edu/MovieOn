package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.time.Period;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ItemOption {

    private BigDecimal salesPrice;
    private Period availableDays;
    private ItemType type;

    public ItemOption(BigDecimal salesPrice, Period availableDays,
        ItemType type) {
        this.salesPrice = salesPrice;
        this.availableDays = availableDays;
        this.type = type;
    }

    public enum ItemType {
        RENTAL, PURCHASE
    }
}
