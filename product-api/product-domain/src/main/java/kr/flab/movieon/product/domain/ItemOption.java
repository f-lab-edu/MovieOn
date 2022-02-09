package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.time.Period;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class ItemOption {

    private BigDecimal salesPrice;
    private Period availableDays;
    private ItemType type;

    protected ItemOption() {

    }

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
