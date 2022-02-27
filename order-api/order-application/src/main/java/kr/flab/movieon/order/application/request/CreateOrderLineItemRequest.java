package kr.flab.movieon.order.application.request;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class CreateOrderLineItemRequest {

    @NotNull(message = "주문 요청 상품 ID는 반드시 필요합니다")
    private Long itemId;
    @NotBlank(message = "주문 요청 상품명은 반드시 필요합니다")
    private String productName;
    @NotNull(message = "주문 요청 상품 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "주문 요청 상품 최소 가격은 0원 이상이어야만 합니다")
    private Long basePrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateOrderLineItemRequest that = (CreateOrderLineItemRequest) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(productName,
            that.productName) && Objects.equals(basePrice, that.basePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, productName, basePrice);
    }
}
