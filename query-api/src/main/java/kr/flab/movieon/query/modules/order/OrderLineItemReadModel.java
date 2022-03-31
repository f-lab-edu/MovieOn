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
}
