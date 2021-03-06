package kr.flab.movieon.order.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 상세 옵션")
public final class CreateOrderItemOptionRequest {

    @Schema(description = "상품 옵션명")
    private String optionName;

    @Schema(description = "상품 옵션 할인 가격")
    private Integer salesPrice;

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Integer salesPrice) {
        this.salesPrice = salesPrice;
    }
}
