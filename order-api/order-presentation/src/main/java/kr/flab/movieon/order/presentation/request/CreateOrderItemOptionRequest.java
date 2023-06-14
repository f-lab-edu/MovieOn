package kr.flab.movieon.order.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Schema(description = "상품 상세 옵션")
public final class CreateOrderItemOptionRequest {

    @Schema(description = "상품 상세 옵션명")
    @NotBlank(message = "상세 옵션명은 비어있을 수 없습니다.")
    private String optionName;

    @Schema(description = "상품 상세 옵션 할인 가격")
    @Min(value = 0, message = "상세 옵션 할인 가격은 0보다 작을 수 없습니다.")
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
