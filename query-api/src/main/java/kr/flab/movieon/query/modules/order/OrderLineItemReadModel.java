package kr.flab.movieon.query.modules.order;

import java.util.List;

public final class OrderLineItemReadModel {

    private Long itemId;
    private String name;
    private Integer basePrice;
    private List<OrderLineItemOptionReadModel> options;

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public List<OrderLineItemOptionReadModel> getOptions() {
        return options;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    public void setOptions(
        List<OrderLineItemOptionReadModel> options) {
        this.options = options;
    }
}
