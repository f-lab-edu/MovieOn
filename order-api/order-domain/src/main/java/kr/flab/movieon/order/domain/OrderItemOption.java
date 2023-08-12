package kr.flab.movieon.order.domain;

import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemOption {

    protected OrderItemOption() {

    }

    private String optionName;
    private BigDecimal salesPrice;

    public OrderItemOption(String optionName, BigDecimal salesPrice) {
        this.optionName = optionName;
        this.salesPrice = salesPrice;
    }

    public String getOptionName() {
        return optionName;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItemOption that = (OrderItemOption) o;
        return Objects.equals(optionName, that.optionName) && Objects.equals(
            salesPrice, that.salesPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionName, salesPrice);
    }
}
