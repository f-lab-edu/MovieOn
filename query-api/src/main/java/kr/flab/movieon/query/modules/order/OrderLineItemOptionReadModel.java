package kr.flab.movieon.query.modules.order;

public final class OrderLineItemOptionReadModel {

    private String optionName;
    private Integer salesPrice;

    public String getOptionName() {
        return optionName;
    }

    public Integer getSalesPrice() {
        return salesPrice;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public void setSalesPrice(Integer salesPrice) {
        this.salesPrice = salesPrice;
    }
}
