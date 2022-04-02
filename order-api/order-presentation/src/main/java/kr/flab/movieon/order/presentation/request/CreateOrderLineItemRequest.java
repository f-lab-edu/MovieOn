package kr.flab.movieon.order.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "주문 항목")
public final class CreateOrderLineItemRequest {

    @Schema(description = "상품ID", example = "1", required = true)
    @NotNull(message = "주문 요청 상품 ID는 반드시 필요합니다")
    private Long itemId;

    @Schema(description = "상품명", example = "(구매) 마이웨이", required = true)
    @NotBlank(message = "주문 요청 상품명은 반드시 필요합니다")
    private String productName;

    @Schema(description = "상품 가격", example = "0", required = true)
    @NotNull(message = "주문 요청 상품 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "주문 요청 상품 최소 가격은 0원 이상이어야만 합니다")
    private Long basePrice;

    @Schema(description = "상품 옵션")
    private List<CreateOrderItemOptionRequest> options;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public List<CreateOrderItemOptionRequest> getOptions() {
        return options;
    }

    public void setOptions(
        List<CreateOrderItemOptionRequest> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "CreateOrderProductRequest{" + "productId=" + itemId + ", productName='"
            + productName + '\'' + ", price=" + basePrice + '}';
    }
}
