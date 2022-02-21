package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.time.Period;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
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

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public Period getAvailableDays() {
        return availableDays;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemOption that = (ItemOption) o;
        return Objects.equals(salesPrice, that.salesPrice) && Objects.equals(
            availableDays, that.availableDays) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(salesPrice, availableDays, type);
    }
}
