package kr.flab.movieon.order.application.request;

import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class CreateOrderProductRequest {

    @NotNull(message = "주문 요청 상품 ID는 반드시 필요합니다")
    private Long productId;
    @NotBlank(message = "주문 요청 상품명은 반드시 필요합니다")
    private String productName;
    @NotNull(message = "주문 요청 상품 금액은 비어있을 수 없습니다")
    @Min(value = 0, message = "주문 요청 상품 최소 가격은 0원 이상이어야만 합니다")
    private Long price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CreateOrderProductRequest{" + "productId=" + productId + ", productName='"
            + productName + '\'' + ", price=" + price + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateOrderProductRequest that = (CreateOrderProductRequest) o;
        return Objects.equals(productId, that.productId) && Objects.equals(productName,
            that.productName) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, price);
    }
}
