package kr.flab.movieon.product.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ItemOption {

    protected ItemOption() {

    }

    private String optionName;  // TODO Enum(화질) 리팩토링
    private BigDecimal salesPrice;

    public ItemOption(String optionName, BigDecimal salesPrice) {
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
        ItemOption that = (ItemOption) o;
        return Objects.equals(optionName, that.optionName) && Objects.equals(
            salesPrice, that.salesPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionName, salesPrice);
    }
}
