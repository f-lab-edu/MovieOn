package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class ItemOption {

    private final String optionName;
    private final BigDecimal salesPrice;

    public ItemOption(String optionName, BigDecimal salesPrice) {
        this.optionName = optionName;
        this.salesPrice = salesPrice;
    }

    public boolean isSatisfiedBy(OrderItemOption orderItemOption) {
        return this.optionName.equals(orderItemOption.getOptionName()) && this.salesPrice.equals(
            orderItemOption.getSalesPrice());
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
        return Objects.equals(optionName, that.optionName) && Objects.equals(
            salesPrice, that.salesPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionName, salesPrice);
    }
}
